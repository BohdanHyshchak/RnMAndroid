package com.bhyshchak.rickandmorty.designsystem.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.bhyshchak.rickandmorty.designsystem.theme.DS

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(DS.dimens.corner),
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = DS.colors.brand,
            contentColor = DS.colors.onBrand,
            disabledContainerColor = DS.colors.disabled,
            disabledContentColor = DS.colors.onDisabled,
        ),
    ) {
        AppText(
            text = text,
            style = DS.typography.label,
            color = if (enabled) DS.colors.onBrand else DS.colors.onDisabled,
        )
    }
}


