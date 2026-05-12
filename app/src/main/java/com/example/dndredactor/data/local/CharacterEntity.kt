package com.example.dndredactor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dndredactor.data.AppConstants

@Entity(tableName = AppConstants.CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val level: Int,
    val classType: String,
    val raceId: String?,
    val createdAt: Long
)