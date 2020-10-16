package com.example.todolistroom.adapter

import android.graphics.Paint
import android.graphics.Typeface
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

class TodoAdapter(val changeState: (idx: Int, state: Boolean) -> Unit): RecyclerView.Adapter<TodoAdapter.Holder>() {
    private var todo: List<Todo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        todo[position].let {
            with(holder) {
                tvTitle.text = it.title

                if (it.state) {
                    tvTitle.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTypeface(null, Typeface.BOLD)
                    }
                } else {
                    tvTitle.apply {
                        paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                        setTypeface(null, Typeface.NORMAL)
                    }
                }

                tvTitle.setOnClickListener {
                    changeState(todo[position].idx!!, !todo[position].state)
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
