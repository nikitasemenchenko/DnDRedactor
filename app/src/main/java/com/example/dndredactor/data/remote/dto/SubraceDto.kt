package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubraceDto(
    val index: String,
    val name: String,
    val desc: String? = null
)