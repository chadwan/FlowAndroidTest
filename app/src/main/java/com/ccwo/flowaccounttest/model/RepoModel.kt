package com.ccwo.flowaccounttest.model

import com.ccwo.flowaccounttest.api.RepoApiResponse
import com.google.gson.annotations.SerializedName

class RepoModel {
    @SerializedName("id")
    var id : String = ""
    @SerializedName("name")
    var name : String = ""
    @SerializedName("full_name")
    var fullName : String = ""
    @SerializedName("owner")
    var owner : Owner = Owner()

    class Owner{
        @SerializedName("avatar_url")
        var avatarUrl : String = ""
    }
}