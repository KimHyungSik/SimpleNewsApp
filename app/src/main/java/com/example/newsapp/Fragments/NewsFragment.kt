package com.example.newsapp.Fragments

import android.content.Context
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.LoadingProgress.NewsLoadingProgress
import com.example.newsapp.MainActivity
import com.example.newsapp.MyApplication
import com.example.newsapp.R
import com.example.newsapp.Recycler.InQueryHistoryRecycler
import com.example.newsapp.Recycler.InRecyclerView
import com.example.newsapp.Recycler.NewsRecyclerAdapter
import com.example.newsapp.Recycler.QueryHistoryRecyclerAdapter
import com.example.newsapp.Repository.DataRepository
import com.example.newsapp.Test.RepositoryDaggerTest
import com.example.newsapp.Utils.SEARCH_TYPE
import com.example.newsapp.Utils.Utility.TAG
import com.example.newsapp.ViewModel.NewsFragmentViewModel
import com.example.newsapp.ViewModel.ViewModelFactory
import com.example.newsapp.databinding.FragmentNewsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// 데이터베이스 접근 할떄 마다 suspend 함수로 계속 코들링 함수를 불러주는데 해결 할 방법 없나?

class NewsFragment : Fragment(), InRecyclerView, InQueryHistoryRecycler{
    private var mBinding: FragmentNewsBinding? = null
    private var title = "뉴스"

    // 플래그 먼트간의 데이터 연결을 위해 activityViewModels 사용
    @Inject
    lateinit var repositoryDaggerTest: RepositoryDaggerTest

    private lateinit var mViewModel: NewsFragmentViewModel
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

        mViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(activity?.application!!)).get(NewsFragmentViewModel::class.java)

        // 리사이클러 설정
        this.newsRecyclerAdapter = NewsRecyclerAdapter(this)
        this.queryHistroyRecyclerAdapter = QueryHistoryRecyclerAdapter(this)

        viewModel()
        viewBinding()
        dataBinding()

        CoroutineScope(Dispatchers.Default).launch {
            repositoryDaggerTest.repositoryTest()
        }

        return mBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
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
                 mBinding!!.title = query?: ""

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

    private fun dataBinding(){
        mBinding!!.title = this.title
       // mDataBinding!!.title = this.title
    }

    private fun viewBinding(){
        // onCreateOptionsMenu 활성화
        setHasOptionsMenu(true)
        mBinding?.mainTopAppBar?.apply {
            navigationIcon = null
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

        mBinding!!.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = newsRecyclerAdapter
        }

        mBinding!!.queryHistoryRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = queryHistroyRecyclerAdapter
        }
    }

    private fun viewModel(){
        // 뉴스 리스트 변경
        mViewModel._newsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.newsRecyclerAdapter.submitList(it)
                this.newsRecyclerAdapter.notifyDataSetChanged()
            }
        })
        // 검색어 저장 변
        mViewModel._queryHistory.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: queryHistory observe")
            this.queryHistroyRecyclerAdapter.submitData(ArrayList(it))
            this.queryHistroyRecyclerAdapter.notifyDataSetChanged()
        })

        if (mViewModel._newsLiveData.value == null) {
            Log.d(TAG, "viewModel: loading data")
            this.mViewModel.getNews()
            CoroutineScope(Dispatchers.Default).launch(){
                mViewModel.getQueryAll()
            }
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
    }
}