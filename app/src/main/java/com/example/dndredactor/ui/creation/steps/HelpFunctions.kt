package com.example.dndredactor.ui.creation.steps

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dndredactor.R
import com.example.dndredactor.ui.theme.ButtonColor
import com.example.dndredactor.ui.theme.LightButtonColor
import com.example.dndredactor.ui.theme.LightColor

@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightButtonColor else ButtonColor,
            contentColor = if (isSelected) Color.Black else LightColor
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text)
            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = text,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    items: List<T>,
    selectedId: String?,
    idSelector: (T) -> String,
    nameSelector: (T) -> String,
    labelRes: Int,
    onSelect: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedName = items.find { idSelector(it) == selectedId }
        ?.let { nameSelector(it) }
        ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(labelRes)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                ),
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightColor,
                focusedContainerColor = LightColor,
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
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(nameSelector(item)) },
                    onClick = {
                        onSelect(idSelector(item))
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DescriptionCard(
    desc: String?,
    placeholder: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LightButtonColor,
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.animateContentSize()
    ) {
        Text(
            text = desc ?: stringResource(placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(8.dp),
            minLines = 3
        )
    }
}

@Composable
fun Title(textRes: Int) {
    Text(
        text = stringResource(textRes),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        color = LightColor
    )
}

@Composable
fun CustomTextField(
    labelRes: Int,
    onChange: (String) -> Unit,
    value: String,
    minLines: Int = 1
){
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(stringResource(labelRes)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        shape = MaterialTheme.shapes.large,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightColor,
            focusedContainerColor = LightColor,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        minLines = minLines
    )
}