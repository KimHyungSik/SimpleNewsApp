package com.example.newsapp.Recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class QueryHistoryRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val queryTextView: TextView = itemView.findViewById(R.id.query_itme_text)
    private val deleteQueryButton: Button = itemView.findViewById(R.id.delete_query_history)

    fun bindWithView(queryText: String){
        queryTextView.text = queryText
    }

}