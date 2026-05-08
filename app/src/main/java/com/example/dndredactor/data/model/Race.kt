package com.example.dndredactor.data.model

data class Race(
    val id: String,
    val name: String,
    val description: String = "",
    val subraces: List<Subrace> = emptyList()
)