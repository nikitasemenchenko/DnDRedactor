package com.example.dndredactor.data.model

data class Character (
    val id: Int,
    val name: String,
    val level: Int,
    val classType: ClassType?,
    val raceId: String?
)