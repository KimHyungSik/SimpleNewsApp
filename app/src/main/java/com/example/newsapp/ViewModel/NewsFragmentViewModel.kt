package com.example.newsapp.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Room.AppDatabase
import com.example.newsapp.Utils.RESPONSE_STATUIS
import com.example.newsapp.Utils.Utility.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

     var dataRepository = DataRepository.getInstance(application)

    var _newsLiveData: MutableLiveData<ArrayList<NewsModel>> = MutableLiveData<ArrayList<NewsModel>>()
    var _queryHistory: MutableLiveData<List<QueryHistory>> = MutableLiveData()

    fun getNews() {
        NewsRetrofitManager.instance.searchHeadlinesNews("", "kr", completion = { responsState, responseDataArrayList ->
            _newsLiveData.postValue(responseDataArrayList)
        })
    }

    fun filter(query: String){
        NewsRetrofitManager.instance.searchHeadlinesNews(query, "kr", completion = { responsState, responseDataArrayList ->
            when(responsState){
                RESPONSE_STATUIS.OK ->{
                    _newsLiveData.postValue(responseDataArrayList)
                }
                RESPONSE_STATUIS.NO_CONTENT ->{
                    Log.d(TAG, "filter: 검색 데이터가 없습니다")
                }
                RESPONSE_STATUIS.ERROR ->{
                    Log.d(TAG, "filter: 서버에러")
                }
            }
        })
    }

    suspend fun getQueryAll(){
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
    }

    suspend fun insertQueryHistory(query: String){
        // TODO: 입력 날짜 추가
        val queryHistory = QueryHistory(null, query, "")
        dataRepository.queryHistoryRepository.insert(queryHistory)
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
        Log.d(TAG, "insertQueryHistory: ${dataRepository.queryHistoryRepository.getAllQueryHistory()}")
    }

    suspend fun deleteQueryHistory(queryHistoryIndex: Int){
        _queryHistory.value?.get(queryHistoryIndex)?.let { dataRepository.queryHistoryRepository.delete(it) }
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
    }
}