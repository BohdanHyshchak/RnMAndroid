package com.bhyshchak.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bhyshchak.rickandmorty.designsystem.widgets.AppButton
import com.bhyshchak.rickandmorty.designsystem.widgets.AppText
import com.bhyshchak.rickandmorty.designsystem.widgets.AppTextField
import com.bhyshchak.rickandmorty.designsystem.theme.AppTheme
import com.bhyshchak.rickandmorty.designsystem.theme.DS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DS.colors.background)
            .padding(DS.dimens.m),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(DS.dimens.m)) {
            AppText(
                text = "Rick & Morty",
                style = DS.typography.title,
                color = DS.colors.textPrimary,
            )
            AppTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                placeholder = "Type something...",
            )
            AppButton(
                text = "Continue",
                onClick = {},
                enabled = name.isNotBlank(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}