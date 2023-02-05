package com.example.films.data.mapper

import com.example.films.data.dto.CountryDto
import com.example.films.data.dto.FilmDto
import com.example.films.data.dto.FilmFromPopularDto
import com.example.films.data.dto.GenreDto
import com.example.films.domain.entity.Country
import com.example.films.domain.entity.Film
import com.example.films.domain.entity.FilmFromPopular
import com.example.films.domain.entity.Genre

fun FilmDto.toEntity() =
    Film(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        posterUrl = posterUrl,
        description = description,
        countries = countries.map { it.toEntity() },
        genres = genres.map { it.toEntity() },
        filmId = filmId,
    )

fun Film.toDto() =
    FilmDto(
        kinopoiskId = kinopoiskId,
        nameRu = nameRu,
        nameEn = nameEn,
        posterUrl = posterUrl,
        description = description,
        countries = countries.map { it.toDto() },
        genres = genres.map { it.toDto() },
        filmId = filmId,
    )

fun CountryDto.toEntity() =
    Country(
        country = country,
    )

fun Country.toDto() =
    CountryDto(
        country = country,
    )

fun GenreDto.toEntity() =
    Genre(
        genre = genre,
    )

fun Genre.toDto() =
    GenreDto(
        genre = genre,
    )

fun FilmFromPopularDto.toEntity() =
    FilmFromPopular(
        id = id,
        filmId = filmId,
        nameRu = nameRu,
        nameEn = nameEn,
        year = year,
        filmLength = filmLength,
        countries = countries.map { it.toEntity() },
        genres = genres.map { it.toEntity() },
        rating = rating,
        ratingVoteCount = ratingVoteCount,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        isFavorite = isFavorite,
    )

fun FilmFromPopular.toDto() =
    FilmFromPopularDto(
        id = id,
        filmId = filmId,
        nameRu = nameRu,
        nameEn = nameEn,
        year = year,
        filmLength = filmLength,
        countries = countries.map { it.toDto() },
        genres = genres.map { it.toDto() },
        rating = rating,
        ratingVoteCount = ratingVoteCount,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        isFavorite = isFavorite,
    )