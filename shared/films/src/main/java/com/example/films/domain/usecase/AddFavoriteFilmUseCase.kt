package com.example.films.domain.usecase

import com.example.films.domain.entity.Film
import com.example.films.domain.repository.FilmsRepository
import javax.inject.Inject

class AddFavoriteFilmUseCase @Inject constructor(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke(film: Film) =
        repository.addFavoriteFilmDescription(film)
}