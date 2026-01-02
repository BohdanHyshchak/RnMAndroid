package com.bhyshchak.rickandmorty.core.di

import androidx.room.Room
import com.bhyshchak.rickandmorty.core.data.local.db.AppDatabase
import com.bhyshchak.rickandmorty.core.data.remote.api.RickAndMortyApi
import com.bhyshchak.rickandmorty.core.data.repository.CharacterRepositoryImpl
import com.bhyshchak.rickandmorty.core.domain.repository.CharacterRepository
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

private val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "rick_and_morty.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

private val repositoryModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(
            api = get(),
            db = get(),
        )
    }
}

private val componentModule = module {
    factory<RootComponent> { (componentContext: com.arkivanov.decompose.ComponentContext) ->
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


