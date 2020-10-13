package com.example.todolistroom.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val idx: Int?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "state")
    val state: Boolean
) {
    constructor(): this(null, "", false)
}
