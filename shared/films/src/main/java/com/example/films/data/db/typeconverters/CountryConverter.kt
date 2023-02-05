package com.example.films.data.db.typeconverters

import androidx.room.TypeConverter
import com.example.films.data.dto.CountryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CountryConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<CountryDto>): String =
        Json.encodeToString(value)

    @TypeConverter
    fun toCountryLangList(value: String): List<CountryDto> =
        Json.decodeFromString(value)
}