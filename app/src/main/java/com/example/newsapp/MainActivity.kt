package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Utils.Utility
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragments_frame) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(mBinding.mainBottomNav, navController)

        NewsRetrofitManager.instance.searchHeadlinesNews("", "kr", completion = {
            responsState, responseDataArrayList ->
            Log.d(Utility.TAG, "onCreate: $responsState")
        })


    }
}