package com.bhyshchak.rickandmorty.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.bhyshchak.rickandmorty.features.root.RootComponent
import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsRoute
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListRoute

@Composable
fun RootContent(component: RootComponent) {
    val detailsSlot = component.detailsSlot.subscribeAsState()
    val detailsComponent = detailsSlot.value.child?.instance

    Box {
        CharactersListRoute(component = component.charactersList)

        if (detailsComponent != null) {
            CharacterDetailsRoute(component = detailsComponent)
        }
    }
}


