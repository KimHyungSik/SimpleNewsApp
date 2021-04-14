package com.example.newsapp.Room.queryHistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueryHistory (
    @PrimaryKey(autoGenerate = true) val qHid: Int,
    @ColumnInfo(name = "qeury") val qeury: String,
    @ColumnInfo(name = "date") val date: String
)