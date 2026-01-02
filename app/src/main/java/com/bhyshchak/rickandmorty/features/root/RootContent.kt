package com.bhyshchak.rickandmorty.features.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsScreen
import com.bhyshchak.rickandmorty.features.characters.list.CharactersListScreen

@Composable
fun RootContent(component: RootComponent) {
    val childStack = component.childStack.subscribeAsState()

    Children(stack = childStack.value) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.CharactersList -> CharactersListScreen(component = instance.component)
            is RootComponent.Child.CharacterDetails -> CharacterDetailsScreen(component = instance.component)
        }
    }
}


