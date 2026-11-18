package com.example.todosync.repository

import com.example.todosync.apiService.ApiService
import com.example.todosync.dao.TodoDao
import com.example.todosync.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val api: ApiService, private val dao: TodoDao) {

    val todos: Flow<List<Todo>> = dao.getAllTodos()

    suspend fun refreshTodos() {
        val remoteData = api.getTodos()
        dao.insertTodo(remoteData)
    }
}