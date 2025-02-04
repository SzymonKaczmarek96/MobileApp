package com.example.myapp

import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.MainApplication.Companion.todoItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.UUID

class TodoViewModel : ViewModel() {
    private val todoItemDao = todoItemDatabase.getTodoDao()
    private val _sortOption = MutableLiveData("Young")

    val _todoList = todoItemDao.getAllTodoItem()
        .map { items -> items.reversed() }
        .asLiveData(viewModelScope.coroutineContext)


    val todoList: LiveData<List<TodoItem>> = MediatorLiveData<List<TodoItem>>().apply{
        addSource(_todoList) { todos ->
            value = sortTodos(todos,_sortOption.value)
        }
        addSource(_sortOption){option ->
            value = sortTodos(_todoList.value,option)
        }

    }

    fun addTodo(todoItemTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = TodoItem(
                id = UUID.randomUUID(),
                title = todoItemTitle,
                createdAt = Date.from(Instant.now()),
                priority = Priority.LOW
            )
            todoItemDao.addTodoItem(newTodo)
        }
    }

    fun deleteTodo(idForDelete: UUID) = viewModelScope.launch(Dispatchers.IO) {
        todoItemDao.deleteTodoItem(idForDelete)
    }

    fun updateTodoPriority(id: UUID, newPriority: Priority) = viewModelScope.launch(Dispatchers.IO) {
        todoItemDao.updateTodoPriority(id, newPriority)
    }

    fun sortedBy(newOption: String) {
        _sortOption.value = newOption
    }


    private fun sortTodos(todos: List<TodoItem>?, option:String?):List<TodoItem>{
        return when(option){
            "Old" -> todos?.sortedBy { it.createdAt } ?: emptyList()
            "Young" -> todos?.sortedByDescending { it.createdAt } ?: emptyList()
            "Priority" -> todos?.sortedByDescending { it.priority } ?: emptyList()
            else -> todos ?: emptyList()
        }
    }
}