package com.example.films.domain.usecase

import com.example.films.domain.repository.FilmsRepository
import javax.inject.Inject

class DeleteFavoriteFilmUseCase @Inject constructor(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke(filmId: Int) =
        repository.deleteFavoriteFilm(filmId)
}