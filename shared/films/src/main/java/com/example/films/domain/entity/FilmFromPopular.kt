package com.example.films.domain.entity

data class FilmFromPopular(
    var id: Int,
    val filmId: Int,
    var nameRu: String,
    val nameEn: String?,
    val year: String,
    val filmLength: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    var visibility: Boolean = true,
    var isFavorite: Boolean = false,
)