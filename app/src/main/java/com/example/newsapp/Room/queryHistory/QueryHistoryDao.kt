package com.example.newsapp.Room.queryHistory

import androidx.room.*

@Dao
interface QueryHistoryDao {

    @Query("SELECT * FROM QueryHistory ORDER BY date")
    fun getAll(): List<QueryHistory>

    // ID 충돌 시 동작 결정
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuery(queryHistory: QueryHistory)

    @Delete
    fun delete(queryHistory: QueryHistory)

}