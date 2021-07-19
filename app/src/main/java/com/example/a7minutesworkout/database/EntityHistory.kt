package com.example.a7minutesworkout.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class Entity (
        @PrimaryKey(autoGenerate = true) val id:Int,
        val time:String
)