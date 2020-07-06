package com.romariomkk.gitrepo.data.api

import com.romariomkk.gitrepo.data.pojo.ServerGithubRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/orgs/square/repos")
    fun getSquareRepos(@Query("page") page: Int, @Query("per_page") size: Int): Single<List<ServerGithubRepo>>
}