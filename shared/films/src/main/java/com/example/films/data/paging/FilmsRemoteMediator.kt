package com.example.films.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.films.data.api.FilmsApi
import com.example.films.data.db.FilmsDB
import com.example.films.data.dto.FilmFromPopularDto
import com.example.films.data.dto.FilmsRemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class FilmsRemoteMediator(
    private val api: FilmsApi,
    private val db: FilmsDB,
) : RemoteMediator<Int, FilmFromPopularDto>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTime = db.filmsRemoteKeysDao().getAllFilmsRemoteKeys().lastOrNull()?.lastUpdated
        cacheTime?.let {
            return if (System.currentTimeMillis() - it > TimeUnit.DAYS.toMillis(1)) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmFromPopularDto>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevPage
            }

            LoadType.APPEND -> {
                //val remoteKeys = getRemoteKeyForLastItem(state)
                val remoteKeys = db.withTransaction {
                    db.filmsRemoteKeysDao().getAllFilmsRemoteKeys().lastOrNull()
                }
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextPage
            }
        }

        try {
            val response = api.getPopularFilms(page = page) // pagesize = 20

            val films = response.films
            val endOfPaginationReached = films.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.filmsRemoteKeysDao().deleteAllFilmsRemoteKeys()
                    db.filmsDao().deleteAllFilms()
                }
                val prevPage = if (page == 1) null else page - 1
                val nextPage = if (endOfPaginationReached) null else page + 1

                val keys = films.map {
                    FilmsRemoteKeys(
                        id = it.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                db.filmsRemoteKeysDao().addAllFilmsRemoteKeys(keys)
                db.filmsDao().addFilms(films)
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmFromPopularDto>,
    ): FilmsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.filmsRemoteKeysDao().getFilmsRemoteKeys(filmId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FilmFromPopularDto>,
    ): FilmsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { film ->
                db.filmsRemoteKeysDao().getFilmsRemoteKeys(filmId = film.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmFromPopularDto>,
    ): FilmsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { film ->
                db.filmsRemoteKeysDao().getFilmsRemoteKeys(filmId = film.id)
            }
    }
}