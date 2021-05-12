package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newsapp.dagger.di.AppComponent
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var mBinding : ActivityMainBinding
    lateinit var mainComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        mainComponent = (application as MyApplication).appComponent
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.fragments_frame) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(mBinding.mainBottomNav, navController)
    }

}