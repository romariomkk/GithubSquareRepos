package com.romariomkk.gitrepo.data.pojo

import com.google.gson.annotations.SerializedName

data class ServerGithubRepo(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String?,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val id: Int,
    val language: String?,
    val name: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    val owner: ServerGithubRepoOwner,
    @SerializedName("private")
    val isPrivate: Boolean,
    val size: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String,
    @SerializedName("watchers_count")
    val watchersCount: Int
)