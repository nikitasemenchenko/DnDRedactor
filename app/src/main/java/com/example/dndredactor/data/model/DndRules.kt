package com.example.dndredactor.data.model

import kotlin.math.floor


fun calculateAbilityModifier(score: Int): Int {
    return floor((score - 10) / 2.0).toInt()
}

fun calculateProficiencyBonus(level: Int): Int {
    return when (level) {
        in 1..4 -> 2
        in 5..8 -> 3
        in 9..12 -> 4
        in 13..16 -> 5
        in 17..20 -> 6
        else -> 2
    }
}

fun textAsModifier(value: Int): String {
    return if(value >= 0) "+$value" else value.toString()
}

fun AbilityScores.getModifier(ability: Ability): Int {
    return calculateAbilityModifier(get(ability))
}