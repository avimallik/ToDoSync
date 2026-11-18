package com.example.todosync

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todosync.adapter.TodoAdapter
import com.example.todosync.databinding.ActivityMainBinding
import com.example.todosync.application.ToDoApplication
import com.example.todosync.viewModel.TodoViewModel
import com.example.todosync.viewModelFactory.TodoViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupRecyclerView()
        observeData()
        viewModel.fetchData()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter()
        binding.recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupViewModel() {
        val repository = (application as ToDoApplication).repository
        val factory = TodoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]
    }

    private fun observeData() {
        viewModel.todos.observe(this) { todoList ->
            if (todoList != null) {
                todoAdapter.submitList(todoList)
                Log.d("TodoSync", "UI updated with: ${todoList.size} items")
            }
        }
    }


}