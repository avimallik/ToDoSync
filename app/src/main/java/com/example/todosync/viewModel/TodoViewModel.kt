package com.example.todosync.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todosync.model.Todo
import com.example.todosync.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val todos = MutableLiveData<List<Todo>>()
    fun fetchData(){
        viewModelScope.launch {
            try {
                repository.refreshTodos()
            }catch (e: Exception){

            }
            todos.postValue(repository.getLocalTodos())
        }
    }
}