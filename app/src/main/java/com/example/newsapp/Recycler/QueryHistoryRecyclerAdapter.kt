package com.example.newsapp.Recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader
import com.example.newsapp.Model.QueryHistory
import com.example.newsapp.R

class QueryHistoryRecyclerAdapter(private var inQueryHistoryRecycler: InQueryHistoryRecycler): RecyclerView.Adapter<QueryHistoryRecyclerHolder>() {

    private var queryArray = ArrayList<QueryHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryHistoryRecyclerHolder {
        return QueryHistoryRecyclerHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.query_history_recycler_item, parent, false),
                this.inQueryHistoryRecycler
        )
    }

    override fun getItemCount(): Int {
        return queryArray.size
    }

    override fun onBindViewHolder(holder: QueryHistoryRecyclerHolder, position: Int) {
        holder.bindWithView(this.queryArray[position].query)
    }

    fun submitData(queryHistory: ArrayList<QueryHistory>){
        this.queryArray = queryHistory
    }

}