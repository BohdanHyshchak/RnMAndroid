package com.bhyshchak.rickandmorty.presentation.characters.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.bhyshchak.rickandmorty.R
import com.bhyshchak.rickandmorty.core.domain.model.CharacterGender
import com.bhyshchak.rickandmorty.core.domain.model.CharacterStatus
import com.bhyshchak.rickandmorty.designsystem.layout.ScreenContainer
import com.bhyshchak.rickandmorty.designsystem.theme.DS
import com.bhyshchak.rickandmorty.designsystem.widgets.AppButton
import com.bhyshchak.rickandmorty.designsystem.widgets.AppText
import com.bhyshchak.rickandmorty.designsystem.widgets.AppTextField
import com.bhyshchak.rickandmorty.features.characters.list.CharactersListComponent
import com.bhyshchak.rickandmorty.presentation.characters.list.model.CharacterListItemViewData
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CharactersListScreen(
    component: CharactersListComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState().value
    val pagingItems = component.characters.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    // Push current scroll position to component state (so it survives navigation to Details).
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .collect { (index, offset) ->
                component.onIntent(CharactersListComponent.Intent.UpdateScrollPosition(index, offset))
            }
    }

    // Restore scroll position once when list is ready (pagingItems.itemCount changes when list loads).
    // Track last restored position to avoid restoring multiple times.
    val lastRestoredPosition = remember { mutableStateOf<Pair<Int, Int>?>(null) }
    LaunchedEffect(pagingItems.itemCount) {
        val savedPosition = state.listScrollIndex to state.listScrollOffset
        if (
            pagingItems.itemCount > 0 &&
            state.listScrollIndex > 0 &&
            pagingItems.itemCount > state.listScrollIndex &&
            lastRestoredPosition.value != savedPosition
        ) {
            lastRestoredPosition.value = savedPosition
            listState.scrollToItem(state.listScrollIndex, state.listScrollOffset)
        }
    }


    ScreenContainer(modifier = modifier) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                if (pagingItems.itemCount == 0) {
                    AppText(
                        text = stringResource(R.string.loading_characters),
                        modifier = Modifier.align(Alignment.Center),
                        style = DS.typography.body,
                        color = DS.colors.textSecondary,
                    )
                }
            }

            is LoadState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(DS.dimens.m),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AppText(
                        text = stringResource(R.string.error_failed_to_load),
                        style = DS.typography.body,
                        color = DS.colors.textPrimary,
                    )
                    AppButton(
                        text = stringResource(R.string.retry),
                        onClick = { pagingItems.retry() },
                    )
                }
                return@ScreenContainer
            }

            else -> Unit
        }

        Column(verticalArrangement = Arrangement.spacedBy(DS.dimens.m)) {
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery,
                onValueChange = { component.onIntent(CharactersListComponent.Intent.UpdateSearchQuery(it)) },
                placeholder = stringResource(R.string.search_placeholder),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DS.dimens.s),
            ) {
                FilterChip(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.filter_status),
                    selectedValue = state.filters.status?.let { status ->
                        stringResource(
                            when (status) {
                                CharacterStatus.Alive -> R.string.character_status_alive
                                CharacterStatus.Dead -> R.string.character_status_dead
                                CharacterStatus.Unknown -> R.string.character_status_unknown
                            }
                        )
                    },
                    onClear = { component.onIntent(CharactersListComponent.Intent.UpdateStatusFilter(null)) },
                    onClick = {
                        val next = when (state.filters.status) {
                            null -> CharacterStatus.Alive
                            CharacterStatus.Alive -> CharacterStatus.Dead
                            CharacterStatus.Dead -> CharacterStatus.Unknown
                            CharacterStatus.Unknown -> null
                        }
                        component.onIntent(CharactersListComponent.Intent.UpdateStatusFilter(next))
                    },
                )

                FilterChip(
                    modifier = Modifier.weight(1f),
                    label = stringResource(R.string.filter_gender),
                    selectedValue = state.filters.gender?.let { gender ->
                        stringResource(
                            when (gender) {
                                CharacterGender.Female -> R.string.character_gender_female
                                CharacterGender.Male -> R.string.character_gender_male
                                CharacterGender.Genderless -> R.string.character_gender_genderless
                                CharacterGender.Unknown -> R.string.character_gender_unknown
                            }
                        )
                    },
                    onClear = { component.onIntent(CharactersListComponent.Intent.UpdateGenderFilter(null)) },
                    onClick = {
                        val next = when (state.filters.gender) {
                            null -> CharacterGender.Female
                            CharacterGender.Female -> CharacterGender.Male
                            CharacterGender.Male -> CharacterGender.Genderless
                            CharacterGender.Genderless -> CharacterGender.Unknown
                            CharacterGender.Unknown -> null
                        }
                        component.onIntent(CharactersListComponent.Intent.UpdateGenderFilter(next))
                    },
                )
            }

            if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh !is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AppText(
                        text = stringResource(R.string.empty_results),
                        style = DS.typography.body,
                        color = DS.colors.textSecondary,
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(DS.dimens.s),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id },
                    ) { index ->
                        val character = pagingItems[index] ?: return@items
                        CharacterListItem(
                            character = character,
                            onClick = { component.onIntent(CharactersListComponent.Intent.OpenDetails(character.id)) },
                        )
                    }

                    item {
                        when (pagingItems.loadState.append) {
                            is LoadState.Loading -> {
                                Spacer(modifier = Modifier.height(DS.dimens.m))
                                AppText(
                                    text = stringResource(R.string.loading_more),
                                    color = DS.colors.textSecondary,
                                )
                            }

                            is LoadState.Error -> {
                                Spacer(modifier = Modifier.height(DS.dimens.m))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    AppText(
                                        text = stringResource(R.string.error_failed_to_load_more),
                                        color = DS.colors.textSecondary,
                                    )
                                    AppButton(
                                        text = stringResource(R.string.retry),
                                        onClick = { pagingItems.retry() },
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterListItem(
    character: CharacterListItemViewData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(DS.dimens.corner))
            .background(DS.colors.surface)
            .clickable(onClick = onClick)
            .padding(DS.dimens.s),
        horizontalArrangement = Arrangement.spacedBy(DS.dimens.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = character.imageUrl,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DS.colors.border),
        )

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(DS.dimens.xs)) {
            AppText(
                text = character.name,
                style = DS.typography.label,
                color = DS.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            AppText(
                text = "${stringResource(character.gender.labelRes)} • ${stringResource(character.status.labelRes)}",
                style = DS.typography.body,
                color = DS.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selectedValue: String?,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(DS.dimens.corner))
            .background(if (selectedValue != null) DS.colors.brand else DS.colors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = DS.dimens.s, vertical = DS.dimens.xs),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            text = if (selectedValue != null) selectedValue else label,
            style = DS.typography.label,
            color = if (selectedValue != null) DS.colors.onBrand else DS.colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        if (selectedValue != null) {
            Spacer(modifier = Modifier.width(DS.dimens.xs))
            AppText(
                text = "✕",
                style = DS.typography.label,
                color = DS.colors.onBrand,
                modifier = Modifier.clickable(onClick = onClear),
            )
        }
    }
}


