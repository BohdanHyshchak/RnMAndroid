package com.bhyshchak.rickandmorty.features.characters.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.bhyshchak.rickandmorty.core.domain.model.CharacterFilters
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemUiModel
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

    private val _state = MutableStateFlow(CharactersListComponent.State())
    override val state: StateFlow<CharactersListComponent.State> = _state.asStateFlow()

    private val debouncedSearchQuery =
        _state
            .map { it.searchQuery }
            .debounce(400L)
            .distinctUntilChanged()
            .stateIn(scope, SharingStarted.Lazily, _state.value.searchQuery)

    private val statusFlow = _state.map { it.filters.status }.distinctUntilChanged()
    private val genderFlow = _state.map { it.filters.gender }.distinctUntilChanged()

    private val filtersFlow =
        combine(
            debouncedSearchQuery,
            statusFlow,
            genderFlow,
        ) { searchQuery, status, gender ->
            CharacterFilters(
                name = searchQuery.takeIf { it.isNotBlank() },
                status = status,
                gender = gender,
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

    override fun onIntent(intent: CharactersListComponent.Intent) {
        when (intent) {
            is CharactersListComponent.Intent.OpenDetails -> onOpenDetails(intent.id)
            is CharactersListComponent.Intent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
            }

            is CharactersListComponent.Intent.UpdateStatusFilter -> {
                _state.value = _state.value.copy(filters = _state.value.filters.copy(status = intent.status))
            }

            is CharactersListComponent.Intent.UpdateGenderFilter -> {
                _state.value = _state.value.copy(filters = _state.value.filters.copy(gender = intent.gender))
            }
        }
    }
}


