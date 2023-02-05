package com.example.films.data.db.typeconverters

import androidx.room.TypeConverter
import com.example.films.data.dto.GenreDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GenreConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<GenreDto>): String =
        Json.encodeToString(value)

    @TypeConverter
    fun toCountryLangList(value: String): List<GenreDto> =
        Json.decodeFromString(value)
}