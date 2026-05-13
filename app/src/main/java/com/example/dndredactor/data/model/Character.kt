package com.example.dndredactor.data.model

data class Character (
    val id: Int,
    val name: String,
    val level: Int,
    val gender: Gender,

    val raceId: String?,
    val raceName: String?,

    val subraceId: String?,
    val subraceName: String?,

    val classType: ClassType,
    val classId: String?,
    val className: String?,

    val archetypeId: String?,
    val archetypeName: String?,

    val appearance: String,
    val personality: String,
    val ideal: String,
    val attachment: String,
    val weakness: String,

    val abilityScores: AbilityScores
)