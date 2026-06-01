package com.example.dndredactor.presentation.diceRoller

import kotlin.math.abs

data class DiceRollerUiState(
    val selectedDiceSides: Int = 20,
    val diceCount: Int = 1,
    val modifier: Int = 0,
    val lastResult: DiceRollResult? = null,
    val history: List<DiceRollResult> = emptyList()
)

data class DiceRollResult(
    val diceCount: Int,
    val diceSides: Int,
    val rolls: List<Int>,
    val modifier: Int,
    val total: Int
) {
    fun getResults(): String{
        val modifierText = when {
            modifier > 0 -> " + $modifier"
            modifier < 0 -> " - ${abs(modifier)}"
            else -> ""
        }
        return "${diceCount}d$diceSides$modifierText"
    }
}