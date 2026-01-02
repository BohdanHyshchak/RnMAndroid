package com.bhyshchak.rickandmorty.features.root

import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.router.stack.ChildStack

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class CharactersList(val component: com.bhyshchak.rickandmorty.features.characters.list.CharactersListComponent) : Child
        data class CharacterDetails(val component: com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent) : Child
    }
}


