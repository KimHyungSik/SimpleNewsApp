package com.example.newsapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentMoreBinding
import com.example.newsapp.databinding.FragmentNewsBinding

class MoreFragment : Fragment() {

        private var mBinding : FragmentMoreBinding? = null

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            val binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
            mBinding = binding

            return mBinding?.root
        }

        override fun onDestroyView() {
            mBinding = null
            super.onDestroyView()
        }

}