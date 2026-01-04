package com.bhyshchak.rickandmorty.core.domain.usecase

import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class ObserveCharacterUseCase(
    private val repository: CharacterRepository,
) {
    operator fun invoke(id: Int): Flow<Character?> =
        repository.observeCharacter(id)
}


