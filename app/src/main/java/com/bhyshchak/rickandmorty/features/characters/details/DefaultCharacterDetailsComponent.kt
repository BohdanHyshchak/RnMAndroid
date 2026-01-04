package com.bhyshchak.rickandmorty.features.characters.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsUiEvent
import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsUiState
import com.bhyshchak.rickandmorty.presentation.characters.model.toDetailsUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultCharacterDetailsComponent(
    componentContext: ComponentContext,
    private val characterId: Int,
    private val repository: CharacterRepository,
    private val onBack: () -> Unit,
) : CharacterDetailsComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(CharacterDetailsUiState())
    override val state: StateFlow<CharacterDetailsUiState> = _state.asStateFlow()

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onDestroy() {
                    scope.cancel()
                }
            }
        )

        scope.launch {
            repository.observeCharacter(characterId)
                .map { it?.toDetailsUiModel() }
                .collectLatest { uiModel ->
                    _state.value = _state.value.copy(character = uiModel)
                }
        }
    }

    override fun onEvent(event: CharacterDetailsUiEvent) {
        when (event) {
            CharacterDetailsUiEvent.BackClicked -> onBack()
        }
    }
}


