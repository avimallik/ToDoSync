package com.example.todosync.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todosync.model.Todo
import com.example.todosync.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val todos: LiveData<List<Todo>> = repository.todos.asLiveData()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun fetchData(){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshTodos()
            }catch (e: Exception){
                Log.e("TodoSync", "Sync failed", e)
                _error.value = "Could not sync. Showing saved list."
            }finally {
                _isLoading.value = false
            }
        }
    }
}