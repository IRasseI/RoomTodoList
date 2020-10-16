package com.example.todolistroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todolistroom.entity.Todo
import com.example.todolistroom.repository.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val repository = TodoRepository(application)
    private val todo = repository.getAll()

    fun getAll(): LiveData<List<Todo>> {
        return todo
    }

    fun insertTodo(todo: Todo) {
        repository.insertTodo(todo)
    }

    fun changeStateTodo(idx: Int, state: Boolean) {
        repository.changeStateTodo(idx, state)
    }

    fun deleteAllTodo () {
        repository.deleteAllTodo()
    }
}
