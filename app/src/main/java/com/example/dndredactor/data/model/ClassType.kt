package com.example.dndredactor.data.model

enum class ClassType {
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
    UNKNOWN; // Неизвестный

    companion object {
        fun fromApiId(apiId: String?): ClassType {
            return when (apiId?.lowercase()) {
                "barbarian" -> BARBARIAN
                "bard" -> BARD
                "cleric" -> CLERIC
                "druid" -> DRUID
                "fighter" -> FIGHTER
                "monk" -> MONK
                "paladin" -> PALADIN
                "ranger" -> RANGER
                "rogue" -> ROGUE
                "sorcerer" -> SORCERER
                "warlock" -> WARLOCK
                "wizard" -> WIZARD
                else -> UNKNOWN
            }
        }

        fun fromStoredName(value: String): ClassType {
            return runCatching {
                valueOf(value)
            }.getOrElse {
                UNKNOWN
            }
        }
    }
}