package com.example.newsapp.Recycler

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Model.NewsModel
import com.example.newsapp.MyApplication
import com.example.newsapp.R
import com.example.newsapp.Utils.Utility.TAG

class NewsRecyclerHolder(itemView : View, inRecyclerView: InRecyclerView) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private val newsImage: ImageView = itemView.findViewById(R.id.recycler_image_view)
    private val newsTitle: TextView = itemView.findViewById(R.id.recycler_title_text)
    private val newsDescription: TextView = itemView.findViewById(R.id.recycler_description_text)
    private val newsItem:CardView = itemView.findViewById(R.id.recyler_card_item)
    private val favoriteBtn: ImageView = itemView.findViewById(R.id.favorite_button)
    private var inRecyclerView : InRecyclerView? = null

    init{
        this.inRecyclerView = inRecyclerView
        this.newsItem.setOnClickListener(this)
        this.favoriteBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "onClick: $v")
        when(v){
            favoriteBtn -> {
                this.inRecyclerView?.onClickedFavorite(adapterPosition)
            }
            newsItem->{
                this.inRecyclerView?.onClickedNewsItem(adapterPosition)
            }
        }
    }

    fun bindWithView(newsModel: NewsModel) {
        newsTitle.text = newsModel.title
        newsDescription.text = newsModel.description

        if(newsModel.urlToImage != "") {
            Glide.with(MyApplication.instance)
                .load(newsModel.urlToImage)
                .placeholder(R.drawable.base_photo_icon)
                .into(newsImage)
        }
    }

}