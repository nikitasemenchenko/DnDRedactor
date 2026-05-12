package com.example.dndredactor.data.model

data class Character (
    val id: Int,
    val name: String,
    val level: Int,
    val gender: Gender,

    val raceId: String?,
    val subraceId: String?,

    val classType: ClassType,
    val classId: String?,
    val archetypeId: String?,

    val appearance: String,
    val personality: String,
    val ideal: String,
    val attachment: String,
    val weakness: String
)