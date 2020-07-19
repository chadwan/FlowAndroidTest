package com.ccwo.flowaccounttest.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ccwo.flowaccounttest.api.RepoApi
import com.ccwo.flowaccounttest.api.RepoApiResponse
import com.ccwo.flowaccounttest.model.RepoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class RepoDataSource : PageKeyedDataSource<Int, RepoModel> {
    private var searchKey: String = ""
    var startPage = 1
    var pageSize = 20
    val networkState = MutableLiveData<NetworkState>()
    private var apiService = RepoApi.create()

    constructor(searchKey: String){
        this.searchKey = searchKey
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RepoModel>) {
        if(searchKey.isNullOrEmpty()){
            searchKey = "android"
        }

        var call : Call<RepoApiResponse> = apiService.getCoins(searchKey,startPage, pageSize)
        call.enqueue(object : Callback<RepoApiResponse> {
            override fun onResponse(call: Call<RepoApiResponse>, response: Response<RepoApiResponse>) {
                if (response.isSuccessful) {
                    var res = response.body()
                    val repoList: MutableList<RepoModel?> = ArrayList()
                    res?.items?.let { repoList.addAll(it) }

                    callback.onResult(repoList, null, startPage+1)
                } else {
                    networkState.postValue(NetworkState.error(response.message()))
                }
            }
            override fun onFailure(call: Call<RepoApiResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error(t.message))
                Log.e("error",t.message)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepoModel>) {
        if(searchKey.isNullOrEmpty()){
            searchKey = "android"
        }

        var call : Call<RepoApiResponse> = apiService.getCoins(searchKey,params.key, pageSize)
        call.enqueue(object : Callback<RepoApiResponse>{
            override fun onResponse(call: Call<RepoApiResponse>, response: Response<RepoApiResponse>) {
                if (response.isSuccessful) {
                    var res = response.body()
                    val repoList: MutableList<RepoModel?> = ArrayList()
                    res?.items?.let { repoList.addAll(it) }

                    callback.onResult(repoList, params.key+1)
                } else {
                    networkState.postValue(NetworkState.error(response.message()))
                }
            }
            override fun onFailure(call: Call<RepoApiResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error(t.message))
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepoModel>) {
        TODO("Not yet implemented")
    }
}