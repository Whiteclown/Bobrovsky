package com.example.films.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PopularFilmsDto(
    val pagesCount: Int,
    val films: List<FilmFromPopularDto>
)