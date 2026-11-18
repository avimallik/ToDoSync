package com.example.todosync.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todosync.R
import com.example.todosync.diffCallBack.TodoDiffCallback
import com.example.todosync.model.Todo

class TodoAdapter: ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    class TodoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todoTitle)
        val status: TextView = view.findViewById(R.id.todoStatus)

        fun bind(todo: Todo) {
            title.text = todo.title
            if (todo.completed){
                status.text = "Done"
                status.setTextColor(Color.parseColor("#2E7D32"))
            }else{
                status.text = "Pending"
                status.setTextColor(Color.parseColor("#757575"))
            }
        }
    }
}