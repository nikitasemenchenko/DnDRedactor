package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto(
    val index: String,
    val name: String,
)