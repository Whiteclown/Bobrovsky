package com.example.films.data.repository

import androidx.paging.*
import com.example.films.data.api.FilmsApi
import com.example.films.data.db.FilmsDB
import com.example.films.data.mapper.toDto
import com.example.films.data.mapper.toEntity
import com.example.films.data.paging.FilmsRemoteMediator
import com.example.films.domain.entity.Film
import com.example.films.domain.entity.FilmFromPopular
import com.example.films.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val api: FilmsApi,
    private val db: FilmsDB,
) : FilmsRepository {

    private val filmsDao = db.filmsDao()

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPopularFilms(): Flow<PagingData<FilmFromPopular>> {
        val pagingSourceFactory = { filmsDao.getAllFilms() }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            remoteMediator = FilmsRemoteMediator(
                api = api,
                db = db,
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pag -> pag.map { it.toEntity() } }
    }

    override suspend fun getFilmDescription(filmId: Int): Film =
        api.getFilmDescription(filmId).toEntity()

    override suspend fun updateFilmFromPopular(film: FilmFromPopular) =
        db.filmsDao().updateFilm(film.toDto())

    override suspend fun addFavoriteFilmDescription(film: Film) =
        db.favoriteFilmsDao().addFavoriteFilm(film.toDto())

    override suspend fun getFavoriteFilmDescription(filmId: Int): Film? =
        db.favoriteFilmsDao().getFavoriteFilm(filmId)?.toEntity()

    override suspend fun deleteFavoriteFilm(filmId: Int) =
        db.favoriteFilmsDao().deleteFavoriteFilm(filmId)
}