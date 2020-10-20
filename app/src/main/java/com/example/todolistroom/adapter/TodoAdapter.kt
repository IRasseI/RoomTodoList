package com.example.todolistroom.adapter

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistroom.R
import com.example.todolistroom.entity.Todo
import kotlinx.android.synthetic.main.fragment_todoitem.view.*

data class CheckedList(
    val idx: Int,
    val state: Boolean
)

class TodoAdapter(
    private val context: Context,
    val changeState: (idx: Int, state: Boolean) -> Unit
): RecyclerView.Adapter<TodoAdapter.Holder>() {
    private var todo: List<Todo> = listOf()
    private var checkedList: ArrayList<CheckedList> = arrayListOf()
    private var lastPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        todo[position].let {Todo ->

            if (position >= checkedList.size) {
                checkedList.add(CheckedList(Todo.idx!!, false))
            }

            setAnimation(holder.itemView, position)

            with(holder) {
                tvTitle.text = Todo.title
                cbList.isChecked = checkedList[position].state

                if (Todo.state) {
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

                cbList.setOnClickListener {
                    checkedList[position] = CheckedList(Todo.idx!!, cbList.isChecked)
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

    fun resetCheckedItem() {
        checkedList = arrayListOf()
        notifyDataSetChanged()
    }

    fun getCheckedItem(): ArrayList<CheckedList> {
        return checkedList;
    }

    private fun setAnimation(v: View, pos: Int) {
        if (pos > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            v.startAnimation(animation)
            lastPosition = pos
        }
    }

    class Holder(v: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(v.context).inflate(R.layout.fragment_todoitem, v, false)
    ) {
        val tvTitle: TextView = itemView.tv_todoitem_title
        val cbList: CheckBox = itemView.cb_list
    }
}
