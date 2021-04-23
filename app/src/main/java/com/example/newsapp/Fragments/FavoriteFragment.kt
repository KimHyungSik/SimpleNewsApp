package com.example.newsapp.Fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Utils.Utility
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), InRecyclerView {

    private var mBinding : FragmentFavoriteBinding? = null

    private val mViewModel: NewsFragmentViewModel by activityViewModels()
    private lateinit var favoriteNewsRecylerAadpter: NewsRecyclerAdapter
    private var mSearchView: SearchView? = null

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
        // onCreateOptionsMenu 활성화
        setHasOptionsMenu(true)

        mBinding?.favoriteFragmentAppBar?.apply {
            navigationIcon = null
            title = "저장한 뉴스"
        }
        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(mBinding?.favoriteFragmentAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_top_bar, menu)

        val searchItem = menu.findItem(R.id.search_top_bar_icon)
        val searchView = searchItem.actionView as SearchView
        mSearchView = searchView
        searchView.apply {
            this.queryHint = "검색어를 입력하세요"
            this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                this.setTextColor(Color.WHITE)
                this.setHintTextColor(Color.WHITE)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
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
        CoroutineScope(Dispatchers.Default).launch {
            mViewModel.deleteFavoriteNews(position)
        }
    }

}