package com.example.myapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapp.TodoItem

@Database(entities = [TodoItem::class], version = 2)
@TypeConverters(Converters::class)
abstract class TodoItemDatabase : RoomDatabase() {

    companion object{
        const val NAME = "Todos_DB"
    }

    abstract fun getTodoDao() : TodoItemDao
}