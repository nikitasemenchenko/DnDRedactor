package com.example.dndredactor.presentation.diceRoller

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DiceRollerViewModel @Inject constructor(): ViewModel(){
    private val _uiState = MutableStateFlow(DiceRollerUiState())
    val uiState = _uiState.asStateFlow()

    fun selectDice(sides: Int){
        _uiState.value = _uiState.value.copy(
            selectedDiceSides = sides
        )
    }

    fun increaseDiceCount(){
        val current = _uiState.value.diceCount
        if (current >= MAX_DICE_COUNT) return

        _uiState.value = _uiState.value.copy(
            diceCount = current + 1
        )
    }

    fun decreaseDiceCount() {
        val current = _uiState.value.diceCount
        if (current <= MIN_DICE_COUNT) return

        _uiState.value = _uiState.value.copy(
            diceCount = current - 1
        )
    }

    fun increaseModifier(){
        val current = _uiState.value.modifier
        if (current >= MAX_MODIFIER) return

        _uiState.value = _uiState.value.copy(
            modifier = current + 1
        )
    }

    fun decreaseModifier() {
        val current = _uiState.value.modifier
        if (current <= MIN_MODIFIER) return

        _uiState.value = _uiState.value.copy(
            modifier = current - 1
        )
    }

    fun rollDice(){
        val state = _uiState.value

        val rolls = List(state.diceCount){
            Random.nextInt(
                from = 1,
                until = state.selectedDiceSides + 1
            )
        }

        val result = DiceRollResult(
            diceCount = state.diceCount,
            diceSides = state.selectedDiceSides,
            rolls = rolls,
            modifier = state.modifier,
            total = rolls.sum() + state.modifier
        )

        _uiState.value = state.copy(
            lastResult = result,
            history = listOf(result) + state.history.take(MAX_HISTORY_SIZE - 1)
        )
    }

    fun clearHistory(){
        _uiState.value = _uiState.value.copy(
            history = emptyList(),
            lastResult = null
        )
    }

    private companion object {
        const val MIN_DICE_COUNT = 1
        const val MAX_DICE_COUNT = 20

        const val MIN_MODIFIER = -30
        const val MAX_MODIFIER = 30

        const val MAX_HISTORY_SIZE = 20
    }
}