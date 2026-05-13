package com.example.dndredactor.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceDto(
    val index: String,
    val name: String,
    val alignment: String? = null,
    val age: String? = null,
    val size: String? = null,
    @SerialName("size_description")
    val sizeDescription: String? = null,
    val subraces: List<ApiResponseDto> = emptyList()
)