package com.example.todolistroom.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistroom.R
import com.example.todolistroom.adapter.TodoAdapter
import com.example.todolistroom.entity.Todo
import com.example.todolistroom.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_delete_todo.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var checkedIdx: List<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun changeState(idx: Int, state: Boolean) {
            todoViewModel.changeStateTodo(idx, state)
        }

        fun checked(idx: Int) {
            Log.i("checked idx", idx.toString())
        }

        val adapter = TodoAdapter(::changeState)
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
            showDeletePopup("TodoList 삭제")
        }
    }

    private fun showDeletePopup (content: String) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete_todo, null)
        val textView: TextView = view.tv_dialog_content
        textView.text = content

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("List 삭제")
            .setPositiveButton("확인") {dialog, whitch ->
                todoViewModel.deleteAllTodo()
                Toast.makeText(applicationContext, "삭제 완료", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("취소") {dialog, whitch ->
            }
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}
