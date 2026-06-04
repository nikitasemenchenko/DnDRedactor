package com.example.dndredactor.presentation.creation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dndredactor.R
import com.example.dndredactor.presentation.components.AppBottomBar
import com.example.dndredactor.presentation.components.AppBottomBarButton

@Composable
fun CharacterCreationBottomBar(
    onFinished: () -> Unit,
    currentStep: CreationStep,
    canGoNext: Boolean,
    goBack: () -> Unit,
    goNext: () -> Unit
) {
    AppBottomBar {
        if (currentStep == CreationStep.IDENTITY) {
            AppBottomBarButton(
                textRes = R.string.next,
                onClick = goNext,
                enabled = canGoNext,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            AppBottomBarButton(
                textRes = R.string.back,
                onClick = goBack,
                modifier = Modifier.weight(1f)
            )

            AppBottomBarButton(
                textRes = if (currentStep == CreationStep.FINAL) {
                    R.string.create
                } else {
                    R.string.next
                },
                onClick = {
                    if (currentStep == CreationStep.FINAL) {
                        onFinished()
                    } else {
                        goNext()
                    }
                },
                enabled = canGoNext,
                modifier = Modifier.weight(1f)
            )
        }
    }
}