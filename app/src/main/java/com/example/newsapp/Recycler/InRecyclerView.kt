package com.example.newsapp.Recycler

interface InRecyclerView {
    // 리스트 클릭시
    fun onClickedNewsItem(position: Int)
    fun onClickedFavorite(position: Int)
}