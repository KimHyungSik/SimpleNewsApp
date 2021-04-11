package com.example.newsapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment(), InRecyclerView {
    private var mBinding : FragmentNewsBinding? = null
    // 플래그 먼트간의 데이터 연결을 위해 activityViewModels 사용
    private val mViewModel : NewsFragmentViewModel by activityViewModels()
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
            if(it != null) {
                this.newsRecyclerAdapter.submitList(it)
                this.newsRecyclerAdapter.notifyDataSetChanged()
            }
        })

        if(mViewModel._newsLiveData.value == null){
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

    override fun onClickedTodoItem(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickedTodoDeleteBtn(position: Int) {
        TODO("Not yet implemented")
    }
}