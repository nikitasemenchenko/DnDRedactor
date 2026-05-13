package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClassDto(
    val index: String,
    val name: String,
    val subclasses: List<ApiResponseDto> = emptyList()
)