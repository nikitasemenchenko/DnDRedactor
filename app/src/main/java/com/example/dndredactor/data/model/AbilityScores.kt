package com.example.dndredactor.data.model

import androidx.annotation.StringRes
import com.example.dndredactor.R

enum class Ability(
    @StringRes val titleRes: Int
) {
    STRENGTH(R.string.strength),
    DEXTERITY(R.string.dexterity),
    CONSTITUTION(R.string.constitution),
    INTELLIGENCE(R.string.intelligence),
    WISDOM(R.string.wisdom),
    CHARISMA(R.string.charisma)
}

enum class AbilityGenerationMethod(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int
) {
    RANDOM(
        titleRes = R.string.random_generation,
        descriptionRes = R.string.random_generation_description
    ),
    POINT_BUY(
        titleRes = R.string.point_buy,
        descriptionRes = R.string.point_buy_description
    )
}

data class AbilityScores(
    val strength: Int = 8,
    val dexterity: Int = 8,
    val constitution: Int = 8,
    val intelligence: Int = 8,
    val wisdom: Int = 8,
    val charisma: Int = 8
) {
    fun get(ability: Ability): Int {
        return when (ability) {
            Ability.STRENGTH -> strength
            Ability.DEXTERITY -> dexterity
            Ability.CONSTITUTION -> constitution
            Ability.INTELLIGENCE -> intelligence
            Ability.WISDOM -> wisdom
            Ability.CHARISMA -> charisma
        }
    }

    fun set(
        ability: Ability,
        value: Int
    ): AbilityScores {
        return when (ability) {
            Ability.STRENGTH -> copy(strength = value)
            Ability.DEXTERITY -> copy(dexterity = value)
            Ability.CONSTITUTION -> copy(constitution = value)
            Ability.INTELLIGENCE -> copy(intelligence = value)
            Ability.WISDOM -> copy(wisdom = value)
            Ability.CHARISMA -> copy(charisma = value)
        }
    }
}