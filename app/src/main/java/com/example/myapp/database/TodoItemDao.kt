package com.example.myapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapp.Priority
import com.example.myapp.TodoItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM TODOS")
    fun getAllTodoItem(): Flow<List<TodoItem>>

    @Insert
    fun addTodoItem(todoItem: TodoItem)

    @Query("Delete FROM TODOS WHERE id = :id")
    fun deleteTodoItem(id: UUID)

    @Query("Update todos SET PRIORITY = :newPriority WHERE id = :id")
    fun updateTodoPriority(id: UUID,newPriority: Priority)

}