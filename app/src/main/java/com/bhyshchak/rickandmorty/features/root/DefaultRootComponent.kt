package com.bhyshchak.rickandmorty.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.features.characters.details.DefaultCharacterDetailsComponent
import com.bhyshchak.rickandmorty.features.characters.list.DefaultCharactersListComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val repository: CharacterRepository,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.CharactersList,
            handleBackButton = true,
            serializer = Config.serializer(),
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, childContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.CharactersList -> RootComponent.Child.CharactersList(
                component = DefaultCharactersListComponent(
                    componentContext = childContext,
                    repository = repository,
                    onOpenDetails = { id -> navigation.push(Config.CharacterDetails(id)) },
                )
            )

            is Config.CharacterDetails -> RootComponent.Child.CharacterDetails(
                component = DefaultCharacterDetailsComponent(
                    componentContext = childContext,
                    characterId = config.id,
                    repository = repository,
                    onBack = { navigation.pop() },
                )
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object CharactersList : Config

        @Serializable
        data class CharacterDetails(val id: Int) : Config
    }
}


