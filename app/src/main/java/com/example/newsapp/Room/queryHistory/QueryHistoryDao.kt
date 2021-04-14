package com.example.newsapp.Room.queryHistory

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.Model.QueryHistory

@Dao
interface QueryHistoryDao {

    @Query("SELECT * FROM QueryHistory")
    fun getAll(): List<QueryHistory>

    // ID 충돌 시 동작 결정
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuery(queryHistory: QueryHistory)

    @Delete
    fun delete(queryHistory: QueryHistory)

}