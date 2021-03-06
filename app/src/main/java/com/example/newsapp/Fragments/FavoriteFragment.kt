package com.example.newsapp.Fragments

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Test.RepositoryDaggerTest
import com.example.newsapp.Utils.Utility
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.ViewModel.ViewModelFactory
import com.example.newsapp.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment(), InRecyclerView {

    private var mBinding : FragmentFavoriteBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mViewModel: NewsFragmentViewModel

    private lateinit var favoriteNewsRecylerAadpter: NewsRecyclerAdapter
    private var mSearchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        mBinding = binding
        mViewModel = ViewModelProvider(this, viewModelFactory).get(NewsFragmentViewModel::class.java)

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
        // onCreateOptionsMenu ?????????
        setHasOptionsMenu(true)

        mBinding?.favoriteFragmentAppBar?.apply {
            navigationIcon = null
            title = "????????? ??????"
        }
        return mBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.Default).launch {
                    mViewModel.searchFavoriteNews(query!!)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Dispatchers.Default).launch {
                    mViewModel.searchFavoriteNews(newText!!)
                }
                return true
            }

        })

        searchView.apply {
            this.queryHint = "???????????? ???????????????"
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