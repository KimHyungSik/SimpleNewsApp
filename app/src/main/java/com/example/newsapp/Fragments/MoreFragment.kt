package com.example.newsapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.ListView.MoreListViewAdapter
import com.example.newsapp.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private var mBinding: FragmentMoreBinding? = null

    val list = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
        mBinding = binding

        addItemList()

        val adapter = MoreListViewAdapter(activity?.applicationContext!!, list)
        mBinding!!.moreList.adapter = adapter

        return mBinding?.root
    }

    fun addItemList() {
        list.add("버전 정보")
        list.add("알림 설정")
        list.add("테마 설정")
        list.add("화면 설정")

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}