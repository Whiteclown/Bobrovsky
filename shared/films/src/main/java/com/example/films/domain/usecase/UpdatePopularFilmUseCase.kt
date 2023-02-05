package com.example.films.domain.usecase

import com.example.films.domain.entity.FilmFromPopular
import com.example.films.domain.repository.FilmsRepository
import javax.inject.Inject

class UpdatePopularFilmUseCase @Inject constructor(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke(film: FilmFromPopular) =
        repository.updateFilmFromPopular(film)
}