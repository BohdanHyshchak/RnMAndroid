package com.bhyshchak.rickandmorty.core.di

import com.arkivanov.decompose.ComponentContext
import androidx.room.Room
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import com.bhyshchak.rickandmorty.core.data.repository.CharacterRepositoryImpl
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
import com.bhyshchak.rickandmorty.core.inmemory.CharactersStore
import com.bhyshchak.rickandmorty.core.inmemory.DefaultCharactersStore
import com.bhyshchak.rickandmorty.features.root.DefaultRootComponent
import com.bhyshchak.rickandmorty.features.root.RootComponent
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

private val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(get())
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    single<RickAndMortyApi> {
        get<Retrofit>().create(RickAndMortyApi::class.java)
    }
}

private val repositoryModule = module {
    single<CharactersStore> { DefaultCharactersStore() }

    single<CharacterRepository> {
        CharacterRepositoryImpl(
            api = get(),
            store = get(),
        )
    }
}

private val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "rick_and_morty.db",
        )
            // MVP “auto-migration”: during development, if schema changes we recreate DB automatically.
            .fallbackToDestructiveMigration(dropAllTables = true)
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .build()
    }

    single { get<AppDatabase>().characterDao() }
}

private val componentModule = module {
    factory<RootComponent> { (componentContext: ComponentContext) ->
        DefaultRootComponent(
            componentContext = componentContext,
            repository = get(),
        )
    }
}

val appModules = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    componentModule,
)


