package com.example.newsapp.Fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LoadingProgress.NewsLoadingProgress
import com.example.newsapp.R
import com.example.newsapp.Recycler.InQueryHistoryRecycler
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Recycler.QueryHistoryRecyclerAdapter
import com.example.newsapp.Utils.SEARCH_TYPE
import com.example.newsapp.Utils.Utility.TAG
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.databinding.FragmentNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 데이터베이스 접근 할떄 마다 suspend 함수로 계속 코들링 함수를 불러주는데 해결 할 방법 없나?

class NewsFragment : Fragment(), InRecyclerView, InQueryHistoryRecycler{
    private var mBinding: FragmentNewsBinding? = null

    // 플래그 먼트간의 데이터 연결을 위해 activityViewModels 사용
    private val mViewModel: NewsFragmentViewModel by activityViewModels()
    private lateinit var newsRecyclerAdapter: NewsRecyclerAdapter
    private lateinit var queryHistroyRecyclerAdapter: QueryHistoryRecyclerAdapter
    private var mSearchView: SearchView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        mBinding = binding

        // 리사이클러 설정
        this.newsRecyclerAdapter = NewsRecyclerAdapter(this)
        this.queryHistroyRecyclerAdapter = QueryHistoryRecyclerAdapter(this)

        mViewModel._newsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.newsRecyclerAdapter.submitList(it)
                this.newsRecyclerAdapter.notifyDataSetChanged()
            }
        })

        mViewModel._queryHistory.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: queryHistory observe")
            this.queryHistroyRecyclerAdapter.submitData(ArrayList(it))
            this.queryHistroyRecyclerAdapter.notifyDataSetChanged()
        })

        if (mViewModel._newsLiveData.value == null) {
            this.mViewModel.getNews()
            CoroutineScope(Dispatchers.Default).launch(){
                mViewModel.getQueryAll()
            }
        }

        mBinding!!.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = newsRecyclerAdapter
        }

        mBinding!!.queryHistoryRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = queryHistroyRecyclerAdapter
        }

        // 로딩 관련
        mViewModel._loading.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView:  $it")
            if(it){
                NewsLoadingProgress.show(childFragmentManager, "dialog")
            }else{
                NewsLoadingProgress.dismiss()
            }
        })

        // onCreateOptionsMenu 활성화
        setHasOptionsMenu(true)
        mBinding?.mainTopAppBar?.apply {
            navigationIcon = null
            title = "뉴스"
        }

        mBinding!!.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val recyclerViewLastposition =mBinding!!.newsRecyclerView.canScrollVertically(1 )
                if(!recyclerViewLastposition){
                    mViewModel.addNwesData()
                }
            }
        })

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

    // 뉴스 선택 -> 사이트 이장
    override fun onClickedNewsItem(position: Int) {
        Log.d(TAG, "onClickedNewsItem: $position")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(mViewModel._newsLiveData.value?.get(position)?.url))
        startActivity(intent)
    }

    // 하트 클릭 뉴스 저장
    override fun onClickedFavorite(position: Int) {
        Log.d(TAG, "onClickedFavorite: ")
        Toast.makeText(activity, "뉴스저장", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.Default).launch {
            mViewModel.insertFavoriteNews(position)
        }
    }

    // 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_top_bar, menu)
        val searchItem = menu.findItem(R.id.search_top_bar_icon)
        val searchView = searchItem.actionView as SearchView
        mSearchView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")

                mViewModel.apply {
                    filter(query!!)
                    CoroutineScope(Dispatchers.Default).launch(){
                        insertQueryHistory(query)
                    }
                }

                searchView.clearFocus()
                mBinding?.mainTopAppBar?.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        searchView.apply {
            this.queryHint = "검색어를 입력하세요"
            this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                this.setTextColor(Color.WHITE)
                this.setHintTextColor(Color.WHITE)
            }
            this.setOnQueryTextFocusChangeListener { v, hasFocus ->
                when(hasFocus){
                    true->{
                        mBinding?.queryHistoryView?.visibility = View.VISIBLE
                        mBinding?.newsRecyclerView?.visibility = View.INVISIBLE
                    }
                    false->{
                        mBinding?.queryHistoryView?.visibility = View.INVISIBLE
                        mBinding?.newsRecyclerView?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.all_contry->{
               mViewModel.changeSearchType(SEARCH_TYPE.EVERYTHING)
           }
            R.id.korea_news->{
                mViewModel.changeSearchContry("kr")
            }
            R.id.jpans_news->{
                mViewModel.changeSearchContry("jp")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickedDeleteQuery(position: Int) {
        Log.d(TAG, "onClickedDeleteQuery: ")
        CoroutineScope(Dispatchers.Default).launch(){
           mViewModel.deleteQueryHistory(position)
        }
    }

    override fun onClickedQueryHistory(position: Int) {
        mSearchView?.setQuery(mViewModel._queryHistory.value?.get(position)?.query, false)
    }

}