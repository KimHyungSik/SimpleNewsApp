package com.example.newsapp.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

class NewsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    var dataRepository = DataRepository.getInstance(application)

    var searchType = SEARCH_TYPE.EVERYTHING
    var searchCountry:String = "kr"

    var _newsLiveData: MutableLiveData<ArrayList<NewsModel>> = MutableLiveData<ArrayList<NewsModel>>()
    var _queryHistory: MutableLiveData<List<QueryHistory>> = MutableLiveData()
    var _favoriteNews: MutableLiveData<ArrayList<FavoriteNewsModel>> = MutableLiveData()
    var _loading: MutableLiveData<Boolean> = MutableLiveData()


    fun getNews() {
        _loading.postValue(true)
        NewsRetrofitManager.instance.searchNews(searchType, "", searchCountry, completion = { responsState, responseDataArrayList ->
            if (responsState == RESPONSE_STATUIS.NO_CONTENT) {
                Toast.makeText(context, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            _newsLiveData.postValue(responseDataArrayList)
            _loading.postValue(false)
        })
        CoroutineScope(Dispatchers.Default).launch {
            _favoriteNews.postValue(dataRepository.favoriteNewsRepository.getAllNews())
        }
    }

    fun filter(query: String) {
        _loading.postValue(true)
        NewsRetrofitManager.instance.searchNews(searchType, query, searchCountry, completion = { responsState, responseDataArrayList ->
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

    fun changeSearchType(searchType: SEARCH_TYPE){
        this.searchType = searchType
        getNews()
    }

    fun changeSearchContry(country: String){
        this.searchType = SEARCH_TYPE.TOPHEADLINES
        this.searchCountry = country
        getNews()
    }
}