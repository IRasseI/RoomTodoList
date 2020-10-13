package com.example.todolistroom.adapter

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistroom.R
import com.example.todolistroom.entity.Todo
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_todoitem.view.*

class TodoAdapter(val checkedListener: (v: View, pos: Int, state: Boolean) -> Unit): RecyclerView.Adapter<TodoAdapter.Holder>() {
    private var todo: List<Todo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        todo[position].let {
            with(holder) {
                tvTitle.text = it.title
                cbState.isChecked = it.state

                cbState.setOnCheckedChangeListener {v, isChecked ->
                    checkedListener(v, position, isChecked)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return todo.size
    }

    fun setTodo(todo: List<Todo>) {
        this.todo = todo
        notifyDataSetChanged()
    }

    class Holder(v: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(v.context).inflate(R.layout.fragment_todoitem, v, false)
    ) {
        val tvTitle: TextView = itemView.tv_todoitem_title
        val cbState: CheckBox = itemView.cb_state
    }
}
