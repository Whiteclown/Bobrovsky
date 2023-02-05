package com.example.films.domain.repository

import androidx.paging.PagingData
import com.example.films.domain.entity.Film
import com.example.films.domain.entity.FilmFromPopular
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    suspend fun getPopularFilms(): Flow<PagingData<FilmFromPopular>>

    suspend fun getFilmDescription(filmId: Int): Film

    suspend fun updateFilmFromPopular(film: FilmFromPopular)

    suspend fun addFavoriteFilmDescription(film: Film)

    suspend fun getFavoriteFilmDescription(filmId: Int): Film?

    suspend fun deleteFavoriteFilm(filmId: Int)
}