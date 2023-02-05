package com.example.films.data.api

import com.example.films.data.dto.FilmDto
import com.example.films.data.dto.PopularFilmsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {

    @GET("/api/v2.2/films/top")
    suspend fun getPopularFilms(@Query("page") page: Int = 1): PopularFilmsDto

    @GET("/api/v2.2/films/{filmId}")
    suspend fun getFilmDescription(@Path("filmId") filmId: Int): FilmDto
}