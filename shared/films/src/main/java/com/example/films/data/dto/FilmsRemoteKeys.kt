package com.example.films.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_remote_keys")
data class FilmsRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)