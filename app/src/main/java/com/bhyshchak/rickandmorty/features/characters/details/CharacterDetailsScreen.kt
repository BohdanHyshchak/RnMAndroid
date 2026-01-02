package com.bhyshchak.rickandmorty.features.characters.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bhyshchak.rickandmorty.R
import com.bhyshchak.rickandmorty.designsystem.theme.DS
import com.bhyshchak.rickandmorty.designsystem.widgets.AppButton
import com.bhyshchak.rickandmorty.designsystem.widgets.AppText
import com.bhyshchak.rickandmorty.features.characters.ui.toUi

@Composable
fun CharacterDetailsScreen(
    component: CharacterDetailsComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.state.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DS.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(DS.dimens.m),
        verticalArrangement = Arrangement.spacedBy(DS.dimens.m),
    ) {
        AppButton(
            text = stringResource(R.string.back),
            onClick = { component.onIntent(CharacterDetailsComponent.Intent.BackClicked) },
            modifier = Modifier.fillMaxWidth(),
        )

        val character = state.character
        if (character == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center,
            ) {
                AppText(
                    text = stringResource(R.string.loading),
                    color = DS.colors.textSecondary,
                )
            }
            return
        }

        val ui = character.toUi()
        AsyncImage(
            model = ui.imageUrl,
            contentDescription = ui.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DS.colors.border),
        )

        Column(verticalArrangement = Arrangement.spacedBy(DS.dimens.s)) {
            AppText(
                text = ui.name,
                style = DS.typography.title,
                color = DS.colors.textPrimary,
            )

            AppText(
                text = "${stringResource(ui.gender.labelRes)} • ${stringResource(ui.status.labelRes)}",
                color = DS.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(DS.dimens.xs))

            AppText(
                text = stringResource(R.string.details_species, ui.species),
                color = DS.colors.textPrimary
            )
            AppText(
                text = stringResource(R.string.details_origin, ui.originName),
                color = DS.colors.textPrimary
            )
            AppText(
                text = stringResource(R.string.details_location, ui.locationName),
                color = DS.colors.textPrimary
            )
            AppText(
                text = stringResource(R.string.details_episodes, ui.episodeCount),
                color = DS.colors.textPrimary
            )
        }
    }
}


