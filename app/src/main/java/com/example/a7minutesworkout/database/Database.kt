package com.example.a7minutesworkout.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityHistory::class],version = 1)
abstract class Database: RoomDatabase() {
    abstract fun dao():Dao
}