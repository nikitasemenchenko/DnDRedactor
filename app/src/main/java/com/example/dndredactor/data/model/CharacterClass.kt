package com.example.dndredactor.data.model

data class CharacterClass(
    val id: String,
    val name: String,
    val description: String = "",
    val archetypes: List<Archetype> = emptyList()
)