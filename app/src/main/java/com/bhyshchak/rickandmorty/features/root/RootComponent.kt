package com.bhyshchak.rickandmorty.features.root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent
import com.bhyshchak.rickandmorty.features.characters.list.CharactersListComponent

interface RootComponent {
    val charactersList: CharactersListComponent
    val detailsSlot: Value<ChildSlot<*, CharacterDetailsComponent>>

}


