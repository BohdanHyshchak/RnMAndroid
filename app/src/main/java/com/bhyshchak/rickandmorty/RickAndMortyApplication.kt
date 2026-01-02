package com.bhyshchak.rickandmorty

import android.app.Application
import com.bhyshchak.rickandmorty.core.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RickAndMortyApplication)
            modules(appModules)
        }
    }
}


