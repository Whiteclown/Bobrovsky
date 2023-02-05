package com.example.bobrovsky.di

import android.content.Context
import androidx.room.Room
import com.example.films.data.api.FilmsApi
import com.example.films.data.db.FilmsDB
import com.example.films.data.repository.FilmsRepositoryImpl
import com.example.films.domain.repository.FilmsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FilmsModule {

    @Provides
    @Singleton
    fun provideFilmsApi(retrofit: Retrofit): FilmsApi =
        retrofit.create()

    @Provides
    @Singleton
    fun provideFilmsDatabase(@ApplicationContext context: Context): FilmsDB =
        Room.databaseBuilder(
            context,
            FilmsDB::class.java,
            "FilmsDB",
        ).build()

    @Provides
    @Singleton
    fun provideFilmsRepository(
        api: FilmsApi,
        db: FilmsDB,
    ): FilmsRepository =
        FilmsRepositoryImpl(api, db)
}