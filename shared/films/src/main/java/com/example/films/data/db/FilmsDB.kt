package com.example.films.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.films.data.db.typeconverters.CountryConverter
import com.example.films.data.db.typeconverters.GenreConverter
import com.example.films.data.dto.FilmDto
import com.example.films.data.dto.FilmFromPopularDto
import com.example.films.data.dto.FilmsRemoteKeys

@Database(
    entities = [FilmFromPopularDto::class, FilmsRemoteKeys::class, FilmDto::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(CountryConverter::class, GenreConverter::class)
abstract class FilmsDB : RoomDatabase() {

    abstract fun filmsDao(): FilmsDao
    abstract fun filmsRemoteKeysDao(): FilmsRemoteKeysDao
    abstract fun favoriteFilmsDao(): FavoriteFilmsDao
}