package com.example.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.NoNetworkConnectionException
import com.example.core.StringResourcesProvider
import com.example.details.R
import com.example.films.domain.usecase.GetFavoriteFilmUseCase
import com.example.films.domain.usecase.GetFilmDescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getFilmDescriptionUseCase: GetFilmDescriptionUseCase,
    private val getFavoriteFilmUseCase: GetFavoriteFilmUseCase,
    private val stringResourcesProvider: StringResourcesProvider,
    private val savedStateHandle: SavedStateHandle,
    private val router: DetailsRouter,
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state
        get() = _state.asStateFlow()

    private val _actions: Channel<DetailsAction> = Channel(Channel.BUFFERED)
    val actions: Flow<DetailsAction> = _actions.receiveAsFlow()

    fun load() {
        if (state.value is DetailsState.Loading) {
            viewModelScope.launch {
                try {
                    savedStateHandle.get<Int>("filmId")?.let {
                        val film = if (savedStateHandle.get<Boolean>("isFavorite") == true) {
                            getFavoriteFilmUseCase(it)
                        } else {
                            getFilmDescriptionUseCase(it)
                        }
                        film?.let {
                            _state.value = DetailsState.Content(
                                film = film,
                            )
                        }
                    }
                } catch (e: java.lang.Exception) {
                    when (e) {
                        is HttpException -> _actions.send(
                            DetailsAction.ShowError(
                                e.code().toString()
                            )
                        )

                        is NoNetworkConnectionException -> _actions.send(
                            DetailsAction.ShowError(
                                stringResourcesProvider.getString(R.string.no_network_error_text)
                            )
                        )

                        else -> _actions.send(
                            DetailsAction.ShowError(
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

    fun routeBack() {
        router.routeBack()
    }
}