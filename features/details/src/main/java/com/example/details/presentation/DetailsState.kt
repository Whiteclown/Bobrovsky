package com.example.details.presentation

import com.example.films.domain.entity.Film

sealed interface DetailsState {

    object Loading : DetailsState

    data class Content(
        val film: Film,
    ) : DetailsState
}