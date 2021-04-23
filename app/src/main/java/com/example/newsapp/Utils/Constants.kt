package com.example.newsapp.Utils

object API{
    const val BASE_URL = "https://newsapi.org/v2/"
    const val BASE_URL_TOPHEADLINES = "https://newsapi.org/v2/top-headlines"
    const val BASE_URL_EVERYTHING = "https://newsapi.org/v2/everything"
    const val API_KEY = "5d8daadee87f42bba6d553f9759ec702"
}

object Utility{
    val TAG = " - 로그"
}

enum class RESPONSE_STATUIS{
    OK,
    ERROR,
    NO_CONTENT
}

enum class SEARCH_TYPE{
    EVERYTHING,
    TOPHEADLINES
}
