package com.bhyshchak.rickandmorty.features.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.bhyshchak.rickandmorty.core.domain.usecase.ObserveCharacterUseCase
import com.bhyshchak.rickandmorty.core.domain.usecase.ObservePagedCharactersUseCase
import com.bhyshchak.rickandmorty.features.characters.details.DefaultCharacterDetailsComponent
import com.bhyshchak.rickandmorty.features.characters.list.DefaultCharactersListComponent
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val observePagedCharacters: ObservePagedCharactersUseCase,
    private val observeCharacter: ObserveCharacterUseCase,
) : RootComponent, ComponentContext by componentContext {

    private val detailsNavigation = SlotNavigation<DetailsConfig>()

    override val charactersList =
        DefaultCharactersListComponent(
            componentContext = this,
            observePagedCharacters = observePagedCharacters,
            onOpenDetails = { id -> detailsNavigation.activate(DetailsConfig(id)) },
        )

    override val detailsSlot: Value<ChildSlot<*, com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent>> =
        childSlot(
            source = detailsNavigation,
            serializer = null,
            handleBackButton = true,
            childFactory = ::createDetailsChild,
        )

    private fun createDetailsChild(
        config: DetailsConfig,
        childContext: ComponentContext,
    ): com.bhyshchak.rickandmorty.features.characters.details.CharacterDetailsComponent =
        DefaultCharacterDetailsComponent(
            componentContext = childContext,
            characterId = config.id,
            observeCharacter = observeCharacter,
            onBack = { detailsNavigation.dismiss() },
        )

    @Parcelize
    private data class DetailsConfig(val id: Int) : Parcelable
}


