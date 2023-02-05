package com.example.films.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.films.data.dto.FilmsRemoteKeys

@Dao
interface FilmsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllFilmsRemoteKeys(filmsRemoteKeys: List<FilmsRemoteKeys>)

    @Query("SELECT * FROM films_remote_keys WHERE id = :filmId")
    suspend fun getFilmsRemoteKeys(filmId: Int): FilmsRemoteKeys?

    @Query("SELECT * FROM films_remote_keys")
    suspend fun getAllFilmsRemoteKeys(): List<FilmsRemoteKeys>

    @Query("DELETE FROM films_remote_keys")
    suspend fun deleteAllFilmsRemoteKeys()
}