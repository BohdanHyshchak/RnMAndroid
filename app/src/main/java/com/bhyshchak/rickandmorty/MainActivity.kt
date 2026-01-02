package com.bhyshchak.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bhyshchak.rickandmorty.designsystem.theme.AppTheme
import com.bhyshchak.rickandmorty.features.root.RootComponent
import com.bhyshchak.rickandmorty.features.root.RootContent
import com.arkivanov.decompose.defaultComponentContext
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root: RootComponent = getKoin().get { parametersOf(defaultComponentContext()) }
        setContent {
            AppTheme {
                RootContent(component = root)
            }
        }
    }
}