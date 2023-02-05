package com.example.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.core.NoNetworkConnectionException
import com.example.core.StringResourcesProvider
import com.example.films.domain.entity.FilmFromPopular
import com.example.films.domain.usecase.*
import com.example.list.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val updatePopularFilmUseCase: UpdatePopularFilmUseCase,
    private val getFilmDescriptionUseCase: GetFilmDescriptionUseCase,
    private val addFavoriteFilmUseCase: AddFavoriteFilmUseCase,
    private val deleteFavoriteFilmUseCase: DeleteFavoriteFilmUseCase,
    private val stringResourcesProvider: StringResourcesProvider,
    private val router: ListRouter,
) : ViewModel() {

    private val _state = MutableStateFlow<ListState>(ListState.Loading)
    val state
        get() = _state.asStateFlow()

    private val _actions: Channel<ListAction> = Channel(Channel.BUFFERED)
    val actions: Flow<ListAction> = _actions.receiveAsFlow()

    fun load() {
        if (state.value is ListState.Loading) {
            viewModelScope.launch {
                try {
                    getPopularFilmsUseCase().cachedIn(viewModelScope).collectLatest {
                        _state.value = ListState.Content(
                            data = it,
                        )
                    }
                } catch (e: java.lang.Exception) {
                    when (e) {
                        is HttpException -> _actions.send(ListAction.ShowError(e.code().toString()))

                        is NoNetworkConnectionException -> _actions.send(
                            ListAction.ShowError(
                                stringResourcesProvider.getString(R.string.no_network_error_text)
                            )
                        )

                        else -> _actions.send(
                            ListAction.ShowError(
                                stringResourcesProvider.getString(
                                    R.string.unknown_error_text
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun changeFavorite(film: FilmFromPopular) {
        viewModelScope.launch {
            try {
                updatePopularFilmUseCase(film)
                if (film.isFavorite) {
                    val favFilm = getFilmDescriptionUseCase(film.filmId)
                    addFavoriteFilmUseCase(favFilm.apply { filmId = film.filmId })
                } else {
                    deleteFavoriteFilmUseCase(film.filmId)
                }
            } catch (e: java.lang.Exception) {
                Log.e("ChangeFav", e.message ?: "")
                when (e) {
                    is HttpException -> _actions.send(ListAction.ShowError(e.code().toString()))

                    is NoNetworkConnectionException -> _actions.send(
                        ListAction.ShowError(
                            stringResourcesProvider.getString(R.string.no_network_error_text)
                        )
                    )

                    else -> _actions.send(
                        ListAction.ShowError(
                            stringResourcesProvider.getString(
                                R.string.unknown_error_text
                            )
                        )
                    )
                }
            }
        }
    }

    fun routeToDetail(film: FilmFromPopular) =
        router.routeToDetail(film.filmId, film.isFavorite)
}