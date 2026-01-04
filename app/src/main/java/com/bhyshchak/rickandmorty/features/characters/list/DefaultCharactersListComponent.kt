package com.bhyshchak.rickandmorty.features.characters.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListUiEvent
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListUiState
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemUiModel
import com.bhyshchak.rickandmorty.presentation.characters.model.toDomain
import com.bhyshchak.rickandmorty.presentation.characters.model.toListItemUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class DefaultCharactersListComponent(
    componentContext: ComponentContext,
    private val repository: CharacterRepository,
    private val onOpenDetails: (Int) -> Unit,
) : CharactersListComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(CharactersListUiState())
    override val state: StateFlow<CharactersListUiState> = _state.asStateFlow()

    private val debouncedSearchQuery =
        _state
            .map { it.searchQuery }
            .debounce(400L)
            .distinctUntilChanged()
            .stateIn(scope, SharingStarted.Lazily, _state.value.searchQuery)

    private val statusFlow = _state.map { it.status }.distinctUntilChanged()
    private val genderFlow = _state.map { it.gender }.distinctUntilChanged()

    private val filtersFlow =
        combine(
            debouncedSearchQuery,
            statusFlow,
            genderFlow,
        ) { searchQuery, status, gender ->
            CharacterFilters(
                name = searchQuery.takeIf { it.isNotBlank() },
                status = status?.toDomain(),
                gender = gender?.toDomain(),
            )
        }.distinctUntilChanged()

    override val characters: Flow<PagingData<CharacterListItemUiModel>> =
        filtersFlow
            .flatMapLatest { filters -> repository.observePagedCharacters(filters).cachedIn(scope) }
            .map { pagingData -> pagingData.map { it.toListItemUiModel() } }

    init {
        lifecycle.subscribe(
            object : Lifecycle.Callbacks {
                override fun onDestroy() {
                    scope.cancel()
                }
            }
        )
    }

    override fun onEvent(event: CharactersListUiEvent) {
        when (event) {
            is CharactersListUiEvent.CharacterClicked -> onOpenDetails(event.id)
            is CharactersListUiEvent.SearchQueryChanged -> _state.value = _state.value.copy(searchQuery = event.query)

            is CharactersListUiEvent.StatusCleared -> _state.value = _state.value.copy(status = null)
            is CharactersListUiEvent.GenderCleared -> _state.value = _state.value.copy(gender = null)

            is CharactersListUiEvent.StatusClicked -> {
                val next =
                    when (event.current) {
                        null -> com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Alive
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Alive ->
                            com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Dead
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Dead ->
                            com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Unknown
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterStatusUi.Unknown -> null
                    }
                _state.value = _state.value.copy(status = next)
            }

            is CharactersListUiEvent.GenderClicked -> {
                val next =
                    when (event.current) {
                        null -> com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Female
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Female ->
                            com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Male
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Male ->
                            com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Genderless
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Genderless ->
                            com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Unknown
                        com.bhyshchak.rickandmorty.presentation.characters.model.CharacterGenderUi.Unknown -> null
                    }
                _state.value = _state.value.copy(gender = next)
            }

            CharactersListUiEvent.RetryClicked -> Unit // handled by Route (pagingItems.retry)
        }
    }
}


