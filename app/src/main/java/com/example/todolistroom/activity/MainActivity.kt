package com.example.todolistroom.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistroom.R
import com.example.todolistroom.adapter.TodoAdapter
import com.example.todolistroom.entity.Todo
import com.example.todolistroom.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun checked(v: View, pos: Int, checked: Boolean) {
            Log.i("pos", pos.toString())
            Log.i("checked", checked.toString())
            todoViewModel.changeStateTodo(checked, pos)
        }

        val adapter = TodoAdapter(::checked)
        rv_todo.adapter = adapter
        rv_todo.layoutManager = LinearLayoutManager(this)
        rv_todo.setHasFixedSize(true)

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getAll().observe(this, {todo ->
            Log.i("items", todo.toString())
            adapter.setTodo(todo!!)
        })

        tv_main_title.setOnClickListener {
            Log.i("items", todoViewModel.getAll().toString())
        }

        iv_add.setOnClickListener {
            val title = et_todo.text.toString()

            val todo = Todo(null, title, false)
            todoViewModel.insertTodo(todo)

            et_todo.setText("")
        }

        iv_delete.setOnClickListener {

        }
    }

    private fun deleteAllTodo () {
    }
}
