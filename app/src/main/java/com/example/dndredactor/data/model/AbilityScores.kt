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

enum class AbilityGenerationMethod(
    val title: String,
    val description: String
) {
    RANDOM(
        title = "Случайная генерация",
        description = "Бросаются 4 кубика d6, худший результат отбрасывается."
    ),
    POINT_BUY(
        title = "Закуп характеристик",
        description = "Вы распределяете 27 очков между характеристиками."
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