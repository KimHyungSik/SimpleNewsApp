package com.example.newsapp.Recycler

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.Utils.Utility.TAG

class QueryHistoryRecyclerHolder(itemView: View, private var inQueryHistoryRecycler: InQueryHistoryRecycler) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private val queryTextView: TextView = itemView.findViewById(R.id.query_itme_text)
    private val deleteQueryButton: Button = itemView.findViewById(R.id.delete_query_history)

    init{
        this.deleteQueryButton.setOnClickListener(this)
        itemView.setOnClickListener(this)
    }

    fun bindWithView(queryText: String){
        queryTextView.text = queryText
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "onClick: ")
        when(v?.id){
            R.id.delete_query_history->{
                this.inQueryHistoryRecycler.onClickedDeleteQuery(adapterPosition)
            }
            R.id.query_history_recycler_item->{
               this.inQueryHistoryRecycler.onClickedQueryHistory(adapterPosition)
            }
        }
    }

}