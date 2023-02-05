package com.example.films.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "films")
data class FilmFromPopularDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val filmId: Int,
    val nameRu: String,
    val nameEn: String?,
    val year: String,
    val filmLength: String,
    val countries: List<CountryDto>,
    val genres: List<GenreDto>,
    val rating: String,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    val isFavorite: Boolean = false
)