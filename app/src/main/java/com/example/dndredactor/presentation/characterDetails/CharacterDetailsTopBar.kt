package com.example.dndredactor.presentation.characterDetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppTopBar

@Composable
fun CharacterDetailsTopBar(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    AppTopBar(
        titleRes = R.string.character_sheet,
        onBack = onBackClick,
        actionIcon = Icons.Default.Edit,
        actionContentDescriptionRes = R.string.edit_character,
        onActionClick = onEditClick
    )
}