package com.example.newsapp.Recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Model.FavoriteNewsModel
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.R
import com.example.newsapp.Utils.NewsDataConverter

class NewsRecyclerAdapter(inRecyclerView: InRecyclerView):
                            RecyclerView.Adapter<NewsRecyclerHolder>()
{

     var newsArray = ArrayList<NewsModel>()
    var iRecycler : InRecyclerView? = null

    init{
        this.iRecycler = inRecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerHolder {
        return NewsRecyclerHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_item, parent,false),
                this.iRecycler!!
        )
    }

    override fun getItemCount(): Int {
        return newsArray.size
    }

    override fun onBindViewHolder(holder: NewsRecyclerHolder, position: Int) {
        val dataItem: NewsModel = this.newsArray[position]
        holder.bindWithView(dataItem)
    }

    fun submitList(newsArray: ArrayList<NewsModel>){
        this.newsArray = newsArray
    }

    fun submitList(favoriteNewsModel: ArrayList<FavoriteNewsModel>, i: Int? = null){
        val newsArray = ArrayList<NewsModel>()
        for(item in favoriteNewsModel){
            newsArray.add(NewsDataConverter.favoriteToNews(item))
        }
        this.newsArray = newsArray
    }
}