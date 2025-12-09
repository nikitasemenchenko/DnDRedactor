package com.example.dndredactor.data.model

import com.example.dndredactor.R

enum class Classes {
    BARBARIAN, // Варвар
    BARD,      // Бард
    CLERIC,    // Жрец
    DRUID,     // Друид
    FIGHTER,   // Воин
    MONK,      // Монах
    PALADIN,   // Паладин
    RANGER,    // Следопыт
    ROGUE,     // Плут
    SORCERER,  // Чародей
    WARLOCK,   // Колдун
    WIZARD,    // Волшебник
    UNKNOWN // Неизвестный
}

fun String.toCharacterClass(): Classes {
    return when (this.lowercase()) {
        "варвар" -> Classes.BARBARIAN
        "бард" -> Classes.BARD
        "жрец" -> Classes.CLERIC
        "друид" -> Classes.DRUID
        "воин" -> Classes.FIGHTER
        "монах" -> Classes.MONK
        "паладин" -> Classes.PALADIN
        "следопыт" -> Classes.RANGER
        "плут" -> Classes.ROGUE
        "чародей" -> Classes.SORCERER
        "колдун" -> Classes.WARLOCK
        "волшебник" -> Classes.WIZARD
        else -> Classes.UNKNOWN
    }
}

fun Character.presentation(): CharacterPresentation {
    return CharacterPresentation(
        id = id,
        name = name,
        level = level,
        characterClass = className.toCharacterClass()
    )
}

fun getClassIcon(classes: Classes): Int {
    val resId = when (classes) {
        Classes.BARBARIAN -> R.drawable.varvar
        Classes.BARD -> R.drawable.bard
        Classes.CLERIC -> R.drawable.zrec
        Classes.DRUID -> R.drawable.druid
        Classes.FIGHTER -> R.drawable.voin
        Classes.MONK -> R.drawable.monah
        Classes.PALADIN -> R.drawable.paladin
        Classes.SORCERER -> R.drawable.charodey
        Classes.ROGUE -> R.drawable.plut
        Classes.WARLOCK -> R.drawable.koldun
        Classes.WIZARD -> R.drawable.volshebnik
        Classes.RANGER -> R.drawable.sledopit
        else -> R.drawable.default_player
    }
    return resId
}