package com.example.newsapp.Fragments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Utils.Utility.TAG
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment(), InRecyclerView {
    private var mBinding: FragmentNewsBinding? = null

    // 플래그 먼트간의 데이터 연결을 위해 activityViewModels 사용
    private val mViewModel: NewsFragmentViewModel by activityViewModels()
    private lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

    private lateinit var mSearchView: SearchView
    private var searchViewEditext: EditText? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        mBinding = binding

        setHasOptionsMenu(true)

        // 리사이클러 설정
        this.newsRecyclerAdapter = NewsRecyclerAdapter(this)

        mViewModel._newsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.newsRecyclerAdapter.submitList(it)
                this.newsRecyclerAdapter.notifyDataSetChanged()
            }
        })

        if (mViewModel._newsLiveData.value == null) {
            this.mViewModel.getNews()
        }

        mBinding!!.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = newsRecyclerAdapter
        }

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onClickedNewsItem(position: Int) {
        Log.d(TAG, "onClickedNewsItem: $position")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(mViewModel._newsLiveData.value?.get(position)?.url))
        startActivity(intent)
    }

    // 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_top_bar, menu)
        Log.d(TAG, "onCreateOptionsMenu: ")

        val searchItme = menu.findItem(R.id.search_top_bar_icon)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItme != null) {
            this.mSearchView = searchItme.actionView as SearchView
        }

        this.mSearchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ")
        return super.onOptionsItemSelected(item)
    }
}