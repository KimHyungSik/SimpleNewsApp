package com.example.newsapp.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Utils.Utility
import com.example.newsapp.ViewModel.FavoriteNewsFragmentViewModel
import com.example.newsapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(), InRecyclerView {

    private var mBinding : FragmentFavoriteBinding? = null

    private val mViewModel: FavoriteNewsFragmentViewModel by activityViewModels()
    private lateinit var favoriteNewsRecylerAadpter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        mBinding = binding

        this.favoriteNewsRecylerAadpter = NewsRecyclerAdapter(this)

        mBinding!!.favoriteNewsRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = favoriteNewsRecylerAadpter
        }

        mViewModel._favoriteNews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                this.favoriteNewsRecylerAadpter.submitList(it)
                this.favoriteNewsRecylerAadpter.notifyDataSetChanged()
            }
        })

        if(mViewModel._favoriteNews.value == null){
                mViewModel.getNews()
        }

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onClickedNewsItem(position: Int) {
        Log.d(Utility.TAG, "onClickedNewsItem: $position")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(mViewModel._favoriteNews.value?.get(position)?.url))
        startActivity(intent)
    }

    override fun onClickedFavorite(position: Int) {
    }

}