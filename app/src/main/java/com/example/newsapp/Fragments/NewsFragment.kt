package com.example.newsapp.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        mBinding = binding

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

        // onCreateOptionsMenu 활성화
        setHasOptionsMenu(true)

        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(mBinding?.mainTopAppBar)
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
        inflater.inflate(R.menu.main_top_bar, menu)
        Log.d(TAG, "onCreateOptionsMenu: $menu")
        val searchItem = menu.findItem(R.id.search_top_bar_icon)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")

                searchView.clearFocus()
                mBinding?.mainTopAppBar?.title = query

                mViewModel.filter(query!!)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.apply {
            this.queryHint = "검색어를 입력하세요"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ")
        return super.onOptionsItemSelected(item)
    }

}