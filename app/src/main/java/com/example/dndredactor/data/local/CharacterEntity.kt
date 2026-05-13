package com.example.dndredactor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dndredactor.data.AppConstants

@Entity(tableName = AppConstants.CHARACTERS_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val gender: String,
    val level: Int,

    val raceId: String?,
    val raceName: String?,

    val subraceId: String?,
    val subraceName: String?,

    val classType: String,
    val classId: String?,
    val className: String?,

    val archetypeId: String?,
    val archetypeName: String?,

    val appearance: String,
    val personality: String,
    val ideal: String,
    val attachment: String,
    val weakness: String,

    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,

    val createdAt: Long
)