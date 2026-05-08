package com.example.dndredactor.presentation.mappers

import com.example.dndredactor.R
import com.example.dndredactor.data.model.ClassType

fun ClassType?.toClassIcon(): Int {
    return when (this) {
        ClassType.BARBARIAN -> R.drawable.varvar
        ClassType.BARD -> R.drawable.bard
        ClassType.CLERIC -> R.drawable.zrec
        ClassType.DRUID -> R.drawable.druid
        ClassType.FIGHTER -> R.drawable.voin
        ClassType.MONK -> R.drawable.monah
        ClassType.PALADIN -> R.drawable.paladin
        ClassType.SORCERER -> R.drawable.charodey
        ClassType.ROGUE -> R.drawable.plut
        ClassType.WARLOCK -> R.drawable.koldun
        ClassType.WIZARD -> R.drawable.volshebnik
        ClassType.RANGER -> R.drawable.sledopit
        ClassType.UNKNOWN -> R.drawable.default_player
        else -> R.drawable.default_player
    }
}