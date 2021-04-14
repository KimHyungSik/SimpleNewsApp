package com.example.newsapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.Room.queryHistory.QueryHistoryDao

@Database(version = 1, entities = arrayOf(QueryHistoryDao::class), exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun queryHistroyDao(): QueryHistoryDao

    companion object{
        private const val DATABASE_NAME = "NEWS-APP-DATABASE"

        fun getInstance(context: Context): AppDatabase{
            return buildDatabase(context)
        }

        private fun buildDatabase(context: Context): AppDatabase{
           return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
               .addCallback(object : RoomDatabase.Callback(){
                   override fun onCreate(db: SupportSQLiteDatabase) {
                       super.onCreate(db)
                   }
               })
               .fallbackToDestructiveMigration()
               .build()
        }
    }
}