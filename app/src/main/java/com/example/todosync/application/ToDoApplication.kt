package com.example.todosync.application

import android.app.Application
import androidx.work.NetworkType
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todosync.database.TodoDatabase
import com.example.todosync.repository.TodoRepository
import com.example.todosync.retrofitInstance.RetrofitInstance
import com.example.todosync.workmanager.SyncWorker
import java.util.concurrent.TimeUnit

class ToDoApplication: Application() {
    val database by lazy { TodoDatabase.getDatabase(this) }
    val apiService by lazy { RetrofitInstance.api }
    val repository by lazy { TodoRepository(apiService, database.todoDao()) }

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncDataWork",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}