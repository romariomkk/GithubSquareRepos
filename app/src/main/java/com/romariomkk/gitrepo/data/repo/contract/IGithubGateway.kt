package com.romariomkk.gitrepo.data.repo.contract

import com.romariomkk.gitrepo.data.pojo.ServerGithubRepo
import io.reactivex.Single

interface IGithubGateway {

    fun getSquareRepos(pageNum: Int, size: Int): Single<List<ServerGithubRepo>>
}