package com.example.dndredactor.presentation.creation.logic

import com.example.dndredactor.data.model.AbilityScores
import kotlin.random.Random

object AbilityScoreGenerator {

    fun generateScores(): AbilityScores {
        return AbilityScores(
            strength = rollAbilityScore(),
            dexterity = rollAbilityScore(),
            constitution = rollAbilityScore(),
            intelligence = rollAbilityScore(),
            wisdom = rollAbilityScore(),
            charisma = rollAbilityScore()
        )
    }

    private fun rollAbilityScore(): Int {
        return List(4) {
            Random.nextInt(from = 1, until = 7)
        }
            .sortedDescending()
            .take(3)
            .sum()
    }
}