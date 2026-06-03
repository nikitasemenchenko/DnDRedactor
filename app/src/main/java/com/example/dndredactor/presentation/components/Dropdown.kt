package com.example.dndredactor.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.dndredactor.presentation.theme.LightButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    items: List<T>,
    selectedId: String?,
    idSelector: (T) -> String,
    nameSelector: (T) -> String,
    @StringRes labelRes: Int,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val selectedName = items
        .find { idSelector(it) == selectedId }
        ?.let(nameSelector)
        .orEmpty()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        TextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(stringResource(labelRes))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightButtonColor,
                focusedContainerColor = LightButtonColor,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(nameSelector(item))
                    },
                    onClick = {
                        onSelect(item)
                        expanded = false
                    }
                )
            }
        }
    }
}