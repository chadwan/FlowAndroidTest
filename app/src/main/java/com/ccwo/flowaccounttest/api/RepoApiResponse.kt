package com.ccwo.flowaccounttest.api

import com.ccwo.flowaccounttest.model.RepoModel
import com.google.gson.annotations.SerializedName

class RepoApiResponse {
    @SerializedName("items")
    var items : List<RepoModel> = ArrayList()
}