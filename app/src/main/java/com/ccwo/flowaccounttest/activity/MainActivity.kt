package com.ccwo.flowaccounttest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccwo.flowaccounttest.R
import com.ccwo.flowaccounttest.adapter.RepoAdapter
import com.ccwo.flowaccounttest.data.NetworkState
import com.ccwo.flowaccounttest.model.RepoModel
import com.ccwo.flowaccounttest.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RepoAdapter
    private lateinit var viewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                searchView.setQuery("",false)
                viewModel.refresh()
                swipeRefresh.isRefreshing = false;
            }, 500)
        }

        initRecycleView()
        setSearchView()
        initAdapter()
    }

    private fun initRecycleView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvRepoList.layoutManager = linearLayoutManager
    }

    private fun setSearchView() {
        searchView.setOnClickListener { searchView.isIconified = false }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText.toString())
                return false
            }
        })
    }

    private fun initAdapter() {
        viewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)

        adapter = RepoAdapter {
            viewModel.retry()
        }

        rcvRepoList.adapter = adapter

        viewModel.repoList.observe(this, Observer<PagedList<RepoModel>> {
            adapter.submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer<NetworkState> {
            adapter.setNetworkState(it)
        })

        performSearch("")
    }

    private fun performSearch(searchKey: String) {
        viewModel.filterTextAll.value = searchKey
        viewModel.repoList.observe(this, Observer<PagedList<RepoModel>> {
            adapter.submitList(it)
        })
    }
}