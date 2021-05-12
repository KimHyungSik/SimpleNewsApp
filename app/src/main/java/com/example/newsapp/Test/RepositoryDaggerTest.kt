package com.example.newsapp.Test

import android.util.Log
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Utils.Utility.TAG
import javax.inject.Inject

class RepositoryDaggerTest @Inject constructor(val dataRepository: DataRepository) {
    suspend fun repositoryTest(){
        Log.d(TAG, "repositoryTest: ${dataRepository.favoriteNewsRepository.getAllNews()}")
    }
}