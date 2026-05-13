package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiListResponseDto(
    val results: List<ApiResponseDto> = emptyList()
)