package com.bhyshchak.rickandmorty.features.characters.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.bhyshchak.rickandmorty.core.domain.model.Character
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DefaultCharacterDetailsComponent(
    componentContext: ComponentContext,
    private val characterId: Int,
    private val repository: CharacterRepository,
    private val onBack: () -> Unit,
) : CharacterDetailsComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(CharacterDetailsComponent.State())
    override val state: StateFlow<CharacterDetailsComponent.State> = _state.asStateFlow()

    override val character: Flow<Character?> = repository.observeCharacter(characterId)

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onDestroy() {
                    scope.cancel()
                }
            }
        )

        scope.launch {
            repository.observeCharacter(characterId).collect { character ->
                _state.value = _state.value.copy(character = character)
            }
        }
    }

    override fun onIntent(intent: CharacterDetailsComponent.Intent) {
        when (intent) {
            CharacterDetailsComponent.Intent.BackClicked -> onBack()
        }
    }
}


