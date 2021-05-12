package com.example.newsapp.Repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.Room.AppDatabase
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.Room.queryHistory.QueryHistoryDao

class QueryHistoryRepository(context: Context) {

    private val queryHistoryDao: QueryHistoryDao by lazy{
        val db = AppDatabase.getInstance(context)
        db.queryHistroyDao()
    }

    private val queryHistory: List<QueryHistory> by lazy{
        queryHistoryDao.getAll()
    }

    suspend fun getAllQueryHistory():List<QueryHistory>{
        return queryHistoryDao.getAll()
    }

    suspend fun insert(queryHistory: QueryHistory){
        queryHistoryDao.insertQuery(queryHistory)
    }

    suspend fun delete(queryHistory: QueryHistory){
        queryHistoryDao.delete(queryHistory)
    }
}