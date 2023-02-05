package com.example.list.presentation

sealed interface ListAction {

    data class ShowError(
        val message: String,
    ) : ListAction
}