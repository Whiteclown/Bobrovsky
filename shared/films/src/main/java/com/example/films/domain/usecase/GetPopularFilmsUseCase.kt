package com.example.films.domain.usecase

import com.example.films.domain.repository.FilmsRepository
import javax.inject.Inject

class GetPopularFilmsUseCase @Inject constructor(
    private val repository: FilmsRepository,
) {

    suspend operator fun invoke() =
        repository.getPopularFilms()
}