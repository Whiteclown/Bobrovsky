package com.example.bobrovsky.di

import com.example.bobrovsky.navigation.Navigator
import com.example.details.presentation.DetailsRouter
import com.example.list.presentation.ListRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator =
        Navigator()

    @Provides
    @Singleton
    fun provideListRouter(navigator: Navigator): ListRouter =
        navigator

    @Provides
    @Singleton
    fun provideDetailsRouter(navigator: Navigator): DetailsRouter =
        navigator
}