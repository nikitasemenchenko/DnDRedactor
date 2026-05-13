package com.example.dndredactor.data.model

enum class Ability(
    val title: String
) {
    STRENGTH("Сила"),
    DEXTERITY("Ловкость"),
    CONSTITUTION("Телосложение"),
    INTELLIGENCE("Интеллект"),
    WISDOM("Мудрость"),
    CHARISMA("Харизма")
}

data class AbilityScores(
    val strength: Int = 0,
    val dexterity: Int = 0,
    val constitution: Int = 0,
    val intelligence: Int = 0,
    val wisdom: Int = 0,
    val charisma: Int = 0
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