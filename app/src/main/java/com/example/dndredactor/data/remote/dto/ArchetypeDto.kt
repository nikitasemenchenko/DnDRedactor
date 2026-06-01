package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArchetypeDto(
    val index: String,
    val name: String,
    val desc: List<String> = emptyList()
)
