package com.example.todolistroom.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todolistroom.dao.TodoDAO
import com.example.todolistroom.database.TodoDatabase
import com.example.todolistroom.entity.Todo
import java.lang.Exception

class TodoRepository(application: Application) {
    private val todoDatabase = TodoDatabase.getInstance(application)
    private val todoDao: TodoDAO = todoDatabase.todoDao()
    private val todo: LiveData<List<Todo>> = todoDao.getAll()

    fun getAll(): LiveData<List<Todo>> {
        return todo
    }

    fun insertTodo(todo: Todo) {
        try {
            val thread = Thread(Runnable {
                todoDao.insertTodo(todo)
            }).start()
        }
        catch (e: Exception) { }
    }

    fun changeStateTodo(idx: Int, state: Boolean) {
        try {
            val thread = Thread(Runnable {
                todoDao.changeStateTodo(idx, state)
            }).start()
        }
        catch (e: Exception) { }
    }

    fun deleteAllTodo() {
        try {
            val thread = Thread(Runnable {
                todoDao.deleteAllTodo()
            }).start()
        }
        catch (e: Exception) { }
    }

    fun deleteTodo(idxList: List<Int>) {
        try {
            val thread = Thread(Runnable {
                todoDao.deleteTodo(idxList)
            }).start()
        }
        catch (e: Exception) { }
    }
}
