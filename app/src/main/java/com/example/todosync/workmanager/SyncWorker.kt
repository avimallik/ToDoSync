package com.example.todosync.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todosync.database.TodoDatabase
import com.example.todosync.repository.TodoRepository
import com.example.todosync.retrofitInstance.RetrofitInstance

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("TodoSync", "SyncWorker started")
        return try {
            val database = TodoDatabase.getDatabase(applicationContext)
            val apiService = RetrofitInstance.api
            val repository = TodoRepository(apiService, database.todoDao())
            repository.refreshTodos()
            Log.d("TodoSync", "SyncWorker finished successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("TodoSync", "SyncWorker failed, will retry", e)
            Result.retry()
        }
    }
}