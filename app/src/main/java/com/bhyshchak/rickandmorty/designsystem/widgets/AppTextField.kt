package com.bhyshchak.rickandmorty.designsystem.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bhyshchak.rickandmorty.designsystem.theme.DS

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = singleLine,
        isError = isError,
        textStyle = DS.typography.body,
        keyboardOptions = keyboardOptions,
        label = label?.let { { AppText(text = it, style = DS.typography.label, color = DS.colors.textSecondary) } },
        placeholder = placeholder?.let { { AppText(text = it, style = DS.typography.body, color = DS.colors.textSecondary.copy(alpha = 0.7f)) } },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = DS.colors.textPrimary,
            unfocusedTextColor = DS.colors.textPrimary,
            disabledTextColor = DS.colors.onDisabled,
            errorTextColor = DS.colors.textPrimary,
            focusedContainerColor = DS.colors.surface,
            unfocusedContainerColor = DS.colors.surface,
            disabledContainerColor = DS.colors.surface,
            errorContainerColor = DS.colors.surface,
            focusedBorderColor = DS.colors.brand,
            unfocusedBorderColor = DS.colors.border,
            disabledBorderColor = DS.colors.border,
            errorBorderColor = DS.colors.error,
            cursorColor = DS.colors.brand,
            errorCursorColor = DS.colors.error,
        ),
    )
}


