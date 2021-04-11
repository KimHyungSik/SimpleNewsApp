package com.example.newsapp.Recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.App
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.R

class NewsRecyclerHolder(itemView : View, inRecyclerView: InRecyclerView) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private val newsImage: ImageView = itemView.findViewById(R.id.recycler_image_view)
    private val newsTitle: TextView = itemView.findViewById(R.id.recycler_title_text)
    private val newsDescription: TextView = itemView.findViewById(R.id.recycler_description_text)

    override fun onClick(v: View?) {
        //TODO 클릭시 해당 사이트 연
    }

    fun bindWithView(newsModel: NewsModel) {
        newsTitle.text = newsModel.title
        newsDescription.text = newsModel.description

        if(newsModel.urlToImage != "") {
            Glide.with(App.instance)
                .load(newsModel.urlToImage)
                .placeholder(R.drawable.base_photo_icon)
                .into(newsImage)
        }
    }

}