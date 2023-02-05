package com.example.films.domain.entity

data class PopularFilms(
    val pagesCount: Int,
    val films: List<FilmFromPopular>
)