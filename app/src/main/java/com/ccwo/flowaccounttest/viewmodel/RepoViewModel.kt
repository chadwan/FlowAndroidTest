package com.ccwo.flowaccounttest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ccwo.flowaccounttest.data.NetworkState
import com.ccwo.flowaccounttest.data.RepoDataFactory
import com.ccwo.flowaccounttest.data.RepoDataSource
import com.ccwo.flowaccounttest.model.RepoModel

class RepoViewModel : ViewModel() {
    var repoList: LiveData<PagedList<RepoModel>>
    private val pageSize = 10
    private var sourceFactory = RepoDataFactory("")

    var filterTextAll = MutableLiveData<String>()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()

        repoList = Transformations.switchMap(filterTextAll) { input: String ->
            sourceFactory = RepoDataFactory(input);
            LivePagedListBuilder<Int, RepoModel>(sourceFactory, config).build()
        }
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<RepoDataSource, NetworkState>(
        sourceFactory.liveData) {
        it.networkState
    }

    fun refresh() {
        sourceFactory.liveData.value?.invalidate()
    }

    fun retry() {
    }
}