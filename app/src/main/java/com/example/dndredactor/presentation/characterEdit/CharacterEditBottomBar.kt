package com.example.dndredactor.presentation.characterEdit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppBottomBar
import com.example.dndredactor.presentation.components.AppBottomBarButton

@Composable
fun CharacterEditBottomBar(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    AppBottomBar {
        AppBottomBarButton(
            textRes = R.string.cancel,
            onClick = onBack,
            modifier = Modifier.weight(1f)
        )

        AppBottomBarButton(
            textRes = R.string.save,
            onClick = onSave,
            modifier = Modifier.weight(1f)
        )
    }
}