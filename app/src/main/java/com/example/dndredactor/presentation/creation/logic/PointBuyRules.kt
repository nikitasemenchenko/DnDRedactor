package com.example.dndredactor.presentation.creation.logic

import com.example.dndredactor.data.model.Ability
import com.example.dndredactor.data.model.AbilityScores

object PointBuyRules {

    private val costByScore = mapOf(
        8 to 0,
        9 to 1,
        10 to 2,
        11 to 3,
        12 to 4,
        13 to 5,
        14 to 7,
        15 to 9
    )

    fun getCost(score: Int): Int? {
        return costByScore[score]
    }

    fun getRemainingPoints(scores: AbilityScores): Int {
        val spent = Ability.entries.sumOf { ability ->
            costByScore[scores.get(ability)] ?: 0
        }

        return CreationLimits.POINT_BUY_BUDGET - spent
    }
}