package com.example.films.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.films.data.dto.FilmFromPopularDto
import com.example.films.domain.entity.FilmFromPopular
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilms(films: List<FilmFromPopularDto>)

    @Update
    suspend fun updateFilm(film: FilmFromPopularDto)

    @Query("SELECT * FROM films")
    fun getAllFilms(): PagingSource<Int, FilmFromPopularDto>

    @Query("SELECT * FROM films WHERE id = :filmId")
    fun getFilm(filmId: Int): Flow<FilmFromPopularDto>

    @Query("DELETE FROM films")
    suspend fun deleteAllFilms()
}