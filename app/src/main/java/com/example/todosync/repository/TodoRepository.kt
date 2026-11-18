package com.example.todosync.repository

import com.example.todosync.apiService.ApiService
import com.example.todosync.dao.TodoDao

class TodoRepository(private val api: ApiService, private val dao: TodoDao) {
    suspend fun refreshTodos() {
        val remoteData = api.getTodos()
        dao.insertTodo(remoteData)
    }

    suspend fun getLocalTodos() = dao.getAllTodos()
}