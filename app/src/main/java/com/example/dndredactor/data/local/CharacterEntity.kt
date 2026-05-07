package com.example.dndredactor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dndredactor.data.AppConstants

@Entity(tableName = AppConstants.CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val level: Int,
    val className: String,
    val raceName: String,
    val createdAt: Long
)