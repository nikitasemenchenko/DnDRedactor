package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.dndredactor.presentation.theme.LightButtonColor

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    minLines: Int = 1
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(labelRes))
        },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightButtonColor,
            focusedContainerColor = LightButtonColor,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black
        ),
        minLines = minLines
    )
}