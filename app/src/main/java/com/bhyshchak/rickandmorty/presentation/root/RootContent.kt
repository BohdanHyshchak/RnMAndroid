package com.bhyshchak.rickandmorty.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.bhyshchak.rickandmorty.features.root.RootComponent
import com.bhyshchak.rickandmorty.presentation.characters.details.CharacterDetailsRoute
import com.bhyshchak.rickandmorty.presentation.characters.list.CharactersListRoute

@Composable
fun RootContent(component: RootComponent) {
    val childStack = component.childStack.subscribeAsState()

    Children(stack = childStack.value) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.CharactersList -> CharactersListRoute(component = instance.component)
            is RootComponent.Child.CharacterDetails -> CharacterDetailsRoute(component = instance.component)
        }
    }
}


