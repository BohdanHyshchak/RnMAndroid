package com.bhyshchak.rickandmorty.presentation.characters.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bhyshchak.rickandmorty.R
import com.bhyshchak.rickandmorty.designsystem.layout.ScreenContainer
import com.bhyshchak.rickandmorty.designsystem.theme.DS
import com.bhyshchak.rickandmorty.designsystem.widgets.AppButton
import com.bhyshchak.rickandmorty.designsystem.widgets.AppText

@Composable
fun CharacterDetailsScreen(
    state: CharacterDetailsUiState,
    onEvent: (CharacterDetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val character = state.character

    ScreenContainer(modifier = modifier) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(DS.dimens.m),
        ) {
            AppButton(
                text = stringResource(R.string.back),
                onClick = { onEvent(CharacterDetailsUiEvent.BackClicked) },
                modifier = Modifier.fillMaxWidth(),
            )

            if (character == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AppText(
                        text = stringResource(R.string.details_not_available),
                        color = DS.colors.textSecondary,
                    )
                }
            } else {

                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(DS.colors.border),
                )

                Column(verticalArrangement = Arrangement.spacedBy(DS.dimens.s)) {
                    AppText(
                        text = character.name,
                        style = DS.typography.title,
                        color = DS.colors.textPrimary,
                    )

                    AppText(
                        text = "${stringResource(character.gender.labelRes)} • ${stringResource(character.status.labelRes)}",
                        color = DS.colors.textSecondary,
                    )

                    Spacer(modifier = Modifier.height(DS.dimens.xs))

                    AppText(text = stringResource(R.string.details_species, character.species), color = DS.colors.textPrimary)
                    AppText(text = stringResource(R.string.details_origin, character.originName), color = DS.colors.textPrimary)
                    AppText(text = stringResource(R.string.details_location, character.locationName), color = DS.colors.textPrimary)
                    AppText(text = stringResource(R.string.details_episodes, character.episodeCount), color = DS.colors.textPrimary)
                }
            }
        }
    }
}


