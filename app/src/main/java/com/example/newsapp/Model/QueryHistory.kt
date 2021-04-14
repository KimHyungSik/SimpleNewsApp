package com.example.newsapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QueryHistory")
data class QueryHistory (
    @PrimaryKey(autoGenerate = true) val qHid: Int?,
    @ColumnInfo(name = "qeury") val qeury: String,
    @ColumnInfo(name = "date") val date: String
)