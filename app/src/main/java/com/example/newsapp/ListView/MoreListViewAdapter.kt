package com.example.newsapp.ListView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.newsapp.R

class MoreListViewAdapter(val context: Context, val list: MutableList<String>): BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(convertView == null) view = LayoutInflater.from(context).inflate(R.layout.more_list_view_item, parent, false)

        val item: String = list[position]
        view!!.findViewById<TextView>(R.id.list_item_text_view).text = item

        return view
    }

    override fun getItem(position: Int): Any {
       return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return list.size
    }


}