package com.ccwo.flowaccounttest.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ccwo.flowaccounttest.model.RepoModel

class RepoDataFactory : DataSource.Factory<Int, RepoModel> {
    var liveData = MutableLiveData<RepoDataSource>()
    var searchKey: String = ""

    constructor(searchKey: String){
        this.searchKey = searchKey
    }
    override fun create(): DataSource<Int, RepoModel> {
        val dataSource = RepoDataSource(searchKey)
        liveData.postValue(dataSource)
        return dataSource
    }
}