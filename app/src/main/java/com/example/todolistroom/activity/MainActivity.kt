package com.example.todolistroom.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistroom.R
import com.example.todolistroom.adapter.TodoAdapter
import com.example.todolistroom.entity.Todo
import com.example.todolistroom.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_delete_todo.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun changeState(idx: Int, state: Boolean) {
            todoViewModel.changeStateTodo(idx, state)
        }

        val adapter = TodoAdapter(::changeState)
        rv_todo.adapter = adapter
        rv_todo.layoutManager = LinearLayoutManager(this)
        rv_todo.setHasFixedSize(true)

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getAll().observe(this, {todo ->
            if (todo.isNotEmpty()) {
                rv_todo.visibility = RecyclerView.VISIBLE
                tv_null.visibility = TextView.GONE
                adapter.setTodo(todo!!)
            }
            else {
                tv_null.visibility = TextView.VISIBLE
                rv_todo.visibility = RecyclerView.GONE
            }
        })

        iv_add.setOnClickListener {
            val title = et_todo.text.toString()

            if (title.isNotEmpty()) {
                val todo = Todo(null, title, false)
                todoViewModel.insertTodo(todo)

                et_todo.setText("")
            }
        }

        iv_delete.setOnClickListener {
            val checked = adapter.getCheckedItem().filter { it.state }

            if (checked.isEmpty()) {
                showDeletePopup("TodoList 삭제", adapter)
            }
            else {
                val idxList = ArrayList<Int>()

                checked.map {
                    idxList.add(it.idx)
                }

                todoViewModel.deleteTodo(idxList)
                adapter.resetCheckedItem()
            }
        }
    }

    private fun showDeletePopup (content: String, adapter: TodoAdapter) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_delete_todo, null)
        val textView: TextView = view.tv_dialog_content
        textView.text = content

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("List 삭제")
            .setPositiveButton("확인") {_, _ ->
                todoViewModel.deleteAllTodo()
                Toast.makeText(applicationContext, "삭제 완료", Toast.LENGTH_SHORT).show()
                adapter.resetCheckedItem()
            }
            .setNeutralButton("취소") {_, _ ->
            }
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}
