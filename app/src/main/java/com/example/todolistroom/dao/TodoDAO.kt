package com.example.todolistroom.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todolistroom.entity.Todo

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo_table")
    fun getAll(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo)

    @Query("UPDATE todo_table SET state = :state WHERE idx = :idx")
    fun changeStateTodo(idx: Int, state: Boolean)

    @Query("DELETE FROM todo_table")
    fun deleteAllTodo()

    @Query("DELETE FROM todo_table WHERE idx in (:idxList)")
    fun deleteTodo(idxList: List<Int>)
}
