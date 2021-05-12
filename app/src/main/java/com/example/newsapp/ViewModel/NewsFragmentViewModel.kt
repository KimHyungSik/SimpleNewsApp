package com.example.newsapp.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Retrofit.NewsRetrofitManager
import com.example.newsapp.Utils.NewsDataConverter
import com.example.newsapp.Utils.RESPONSE_STATUIS
import com.example.newsapp.Utils.SEARCH_TYPE
import com.example.newsapp.Utils.Utility.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragmentViewModel @Inject constructor(val dataRepository: DataRepository) : ViewModel() {

    companion object {
        private var instance: NewsFragmentViewModel? = null
        fun getInstance(dataRepository: DataRepository) = instance ?: synchronized(NewsFragmentViewModel::class.java) {
            instance ?: NewsFragmentViewModel(dataRepository).also { instance = it }
        }
    }

    var query = ""

    var searchType = SEARCH_TYPE.EVERYTHING
    var searchCountry:String = "kr"
    var page = 1

    var _newsLiveData: MutableLiveData<ArrayList<NewsModel>> = MutableLiveData<ArrayList<NewsModel>>()
    var _queryHistory: MutableLiveData<List<QueryHistory>> = MutableLiveData()
    var _favoriteNews: MutableLiveData<ArrayList<FavoriteNewsModel>> = MutableLiveData()
    var _loading: MutableLiveData<Boolean> = MutableLiveData()


    fun getNews() {
        _loading.postValue(true)
        this.query = ""
        this.page = 1
        NewsRetrofitManager.instance.searchNews(searchType, "", searchCountry, page, completion = { responsState, responseDataArrayList ->
            _newsLiveData.postValue(responseDataArrayList)
            _loading.postValue(false)
        })
        CoroutineScope(Dispatchers.Default).launch {
            _favoriteNews.postValue(dataRepository.favoriteNewsRepository.getAllNews())
        }
    }

    fun filter(query: String) {
        _loading.postValue(true)
        this.query = query
        NewsRetrofitManager.instance.searchNews(searchType, this.query, searchCountry, page, completion = { responsState, responseDataArrayList ->
            when (responsState) {
                RESPONSE_STATUIS.OK -> {
                    _newsLiveData.postValue(responseDataArrayList)
                }
                RESPONSE_STATUIS.NO_CONTENT -> {
                    Log.d(TAG, "filter: 검색 데이터가 없습니다")
                }
                RESPONSE_STATUIS.ERROR -> {
                    Log.d(TAG, "filter: 서버에러")
                }
            }
            _loading.postValue(false)
        })
    }
    
    fun addNwesData(){
        _loading.postValue(true)
        page += 1
        NewsRetrofitManager.instance.searchNews(searchType, this.query, searchCountry, page, completion = { responsState, responseDataArrayList ->
            when (responsState) {
                RESPONSE_STATUIS.OK -> {
                    val oldList = _newsLiveData.value
                    responseDataArrayList?.forEach { 
                        oldList!!.add(it)
                    }
                    Log.d(TAG, "addNwesData: $oldList")
                    _newsLiveData.postValue(oldList)
                }
                RESPONSE_STATUIS.NO_CONTENT -> {
                    Log.d(TAG, "filter: 검색 데이터가 없습니다")
                }
                RESPONSE_STATUIS.ERROR -> {
                    Log.d(TAG, "filter: 서버에러")
                }
            }
            _loading.postValue(false)
        })
    }
    
    fun changeSearchType(searchType: SEARCH_TYPE){
        this.searchType = searchType
        getNews()
    }

    fun changeSearchContry(country: String){
        this.searchType = SEARCH_TYPE.TOPHEADLINES
        this.searchCountry = country
        getNews()
    }
    
    // === query history
    suspend fun getQueryAll() {
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
    }

    suspend fun insertQueryHistory(query: String) {
        // TODO: 입력 날짜 추가
        val queryHistory = QueryHistory(null, query, "")
        dataRepository.queryHistoryRepository.insert(queryHistory)
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
        Log.d(TAG, "insertQueryHistory: ${dataRepository.queryHistoryRepository.getAllQueryHistory()}")
    }

    suspend fun deleteQueryHistory(queryHistoryIndex: Int) {
        _queryHistory.value?.get(queryHistoryIndex)?.let { dataRepository.queryHistoryRepository.delete(it) }
        _queryHistory.postValue(dataRepository.queryHistoryRepository.getAllQueryHistory())
    }

    suspend fun insertFavoriteNews(newsIndex: Int) {
        dataRepository.favoriteNewsRepository.insert(NewsDataConverter.newsToFavorite(_newsLiveData.value?.get(newsIndex)!!))
        _favoriteNews.postValue(dataRepository.favoriteNewsRepository.getAllNews())
    }

    suspend fun deleteFavoriteNews(newsIndex: Int) {
        dataRepository.favoriteNewsRepository.delete(_favoriteNews.value?.get(newsIndex)!!)
        _favoriteNews.postValue(dataRepository.favoriteNewsRepository.getAllNews())
    }

    suspend fun searchFavoriteNews(query: String){
        _favoriteNews.postValue(dataRepository.favoriteNewsRepository.searchTitle(query))
    }


}