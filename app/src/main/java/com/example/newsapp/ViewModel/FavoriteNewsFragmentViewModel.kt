package com.example.newsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteNewsFragmentViewModel(application: Application): AndroidViewModel(application) {
    var dataRipository = DataRepository.getInstance(application)

    var _favoriteNews: MutableLiveData<ArrayList<FavoriteNewsModel>> = MutableLiveData()

    fun getNews(){
        CoroutineScope(Dispatchers.Default).launch {
            _favoriteNews.postValue(dataRipository.favoriteNewsRepository.getAllNews())
        }
    }
}