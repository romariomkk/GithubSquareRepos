package com.romariomkk.gitrepo.domain.api

import com.romariomkk.gitrepo.domain.pojo.git.server.ServerGithubRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/orgs/square/repos")
    fun getSquareRepos(@Query("page") page: Int, @Query("per_page") size: Int): Single<List<ServerGithubRepo>>
}