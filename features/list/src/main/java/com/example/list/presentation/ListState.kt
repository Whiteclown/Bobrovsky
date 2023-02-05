package com.example.list.presentation

import androidx.paging.PagingData
import com.example.films.domain.entity.FilmFromPopular

sealed interface ListState {

    object Loading : ListState

    data class Content(
        val data: PagingData<FilmFromPopular>,
    ) : ListState
}