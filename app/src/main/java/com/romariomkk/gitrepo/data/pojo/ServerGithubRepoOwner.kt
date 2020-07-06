package com.romariomkk.gitrepo.data.pojo

import com.google.gson.annotations.SerializedName

data class ServerGithubRepoOwner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val id: Int,
    val login: String,
    val url: String
)