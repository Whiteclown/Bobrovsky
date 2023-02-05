package com.example.films.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.films.data.dto.FilmDto
import com.example.films.data.dto.FilmsRemoteKeys

@Dao
interface FavoriteFilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFilm(film: FilmDto)

    @Query("SELECT * FROM favorite_films WHERE filmId = :filmId")
    suspend fun getFavoriteFilm(filmId: Int): FilmDto?

    @Query("DELETE FROM favorite_films WHERE filmId = :filmId")
    suspend fun deleteFavoriteFilm(filmId: Int)
}