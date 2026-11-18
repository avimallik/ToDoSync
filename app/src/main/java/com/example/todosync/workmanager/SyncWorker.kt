package com.example.todosync.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todosync.database.TodoDatabase
import com.example.todosync.repository.TodoRepository
import com.example.todosync.retrofitInstance.RetrofitInstance

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val database = TodoDatabase.getDatabase(applicationContext)
            val apiService = RetrofitInstance.api
            val repository = TodoRepository(apiService, database.todoDao())
            repository.refreshTodos()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}