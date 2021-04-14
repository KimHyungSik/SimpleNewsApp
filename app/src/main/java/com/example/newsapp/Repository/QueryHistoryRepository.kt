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

    private val queryHistory: LiveData<List<QueryHistory>> by lazy{
        queryHistoryDao.getAll()
    }

    fun getAllQueryHistory():LiveData<List<QueryHistory>>{
        return queryHistory
    }

    fun insert(queryHistory: QueryHistory){
        queryHistoryDao.insertQuery(queryHistory)
    }
}