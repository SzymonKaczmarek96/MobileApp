package com.example.myapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "todos")
data class TodoItem(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val createdAt: Date,
    var priority: Priority)


enum class Priority {
    LOW, MEDIUM, HIGH
}


