package com.example.films.domain.usecase

import com.example.films.domain.repository.FilmsRepository
import javax.inject.Inject

class GetFavoriteFilmUseCase @Inject constructor(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke(filmId: Int) =
        repository.getFavoriteFilmDescription(filmId)
}