package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Utils.Utility

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        NewsRetrofitManager.instance.searchHeadlinesNews("", "kr", completion = {
            responsState, responseDataArrayList ->
            Log.d(Utility.TAG, "onCreate: $responsState")
        })
    }
}