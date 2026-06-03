package com.example.dndredactor.presentation.mainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.dndredactor.data.model.Character
import kotlinx.coroutines.delay

@Composable
fun CharacterList(
    characters: List<Character>,
    onDeleteRequest: (Character) -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    val firstLoad = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        delay(100)
        firstLoad.value = false
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            AnimatedVisibility(
                visible = !firstLoad.value,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(700)
                ) + fadeIn()
            ) {
                CharacterCard(
                    character = character,
                    onClick = {
                        onCharacterClick(character.id)
                    },
                    onDelete = {
                        onDeleteRequest(character)
                    }
                )
            }
        }
    }
}