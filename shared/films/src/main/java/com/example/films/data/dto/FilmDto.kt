package com.example.films.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favorite_films")
data class FilmDto(
    @PrimaryKey(autoGenerate = false)
    val kinopoiskId: Int,
    val nameRu: String,
    val nameEn: String?,
    val posterUrl: String,
    val description: String,
    val countries: List<CountryDto>,
    val genres: List<GenreDto>,
    var filmId: Int = 0
)
