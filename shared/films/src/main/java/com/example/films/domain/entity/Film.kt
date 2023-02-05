package com.example.films.domain.entity

data class Film(
    val kinopoiskId: Int,
    val nameRu: String,
    val nameEn: String?,
    val posterUrl: String,
    val description: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    var filmId: Int
)
