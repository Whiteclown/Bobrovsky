package com.example.list.presentation

interface ListRouter {

    fun routeToDetail(filmId: Int, isFavorite: Boolean)
}