package com.romariomkk.gitrepo.domain.repo.contract

import com.romariomkk.gitrepo.domain.pojo.git.server.ServerGithubRepo
import io.reactivex.Single

interface IGithubGateway {

    fun getSquareRepos(pageNum: Int, size: Int): Single<List<ServerGithubRepo>>
}