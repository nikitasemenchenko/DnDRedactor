package com.example.dndredactor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DndDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}