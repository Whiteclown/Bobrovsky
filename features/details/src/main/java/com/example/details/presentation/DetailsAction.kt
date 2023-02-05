package com.example.details.presentation

sealed interface DetailsAction {

    data class ShowError(
        val message: String,
    ) : DetailsAction
}