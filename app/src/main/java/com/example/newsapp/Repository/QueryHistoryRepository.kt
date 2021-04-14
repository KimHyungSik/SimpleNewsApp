package com.example.newsapp.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.newsapp.Room.AppDatabase
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.Room.queryHistory.QueryHistoryDao

class QueryHistoryRepository(application: Application) {

    private val queryHistoryDao: QueryHistoryDao by lazy{
        val db = AppDatabase.getInstance(application)
        db.queryHistroyDao()
    }

    private val queryHistory: List<QueryHistory> by lazy{
        queryHistoryDao.getAll()
    }

    suspend fun getAllQueryHistory():List<QueryHistory>{
        return queryHistory
    }

    suspend fun insert(queryHistory: QueryHistory){
        queryHistoryDao.insertQuery(queryHistory)
    }
}