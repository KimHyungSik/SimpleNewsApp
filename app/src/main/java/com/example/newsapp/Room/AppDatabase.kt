package com.example.newsapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.Room.favoriteList.FavoriteNewsDao
import com.example.newsapp.Room.queryHistory.QueryHistoryDao
// Room Controller
@Database(version = 2, entities = [QueryHistory::class, FavoriteNewsModel::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    public abstract fun queryHistroyDao(): QueryHistoryDao
    public abstract fun favoriteNewsModel(): FavoriteNewsDao

    companion object{
        private const val DATABASE_NAME = "NEWS-APP-DATABASE"

        public fun getInstance(context: Context): AppDatabase{
            return buildDatabase(context)
        }

        private fun buildDatabase(context: Context): AppDatabase{
           return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
               .addCallback(object : RoomDatabase.Callback(){
                   // 데이터 베이스가 처음 생성되었을 때
                   override fun onCreate(db: SupportSQLiteDatabase) {
                       super.onCreate(db)
                   }
               })
               .fallbackToDestructiveMigration()
               .build()
        }
    }
}