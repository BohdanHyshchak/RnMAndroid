package com.bhyshchak.rickandmorty.features.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent
import com.bhyshchak.rickandmorty.features.characters.list.CharactersListComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class CharactersList(val component: CharactersListComponent) : Child
        data class CharacterDetails(val component: CharacterDetailsComponent) : Child
    }
}


