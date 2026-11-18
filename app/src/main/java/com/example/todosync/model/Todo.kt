package com.example.todosync.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo (
    @PrimaryKey val id: Int,
    val title: String,
    val completed: Boolean
)