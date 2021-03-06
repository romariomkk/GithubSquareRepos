package com.romariomkk.gitrepo.data.repo

import com.romariomkk.gitrepo.data.api.GithubApi
import com.romariomkk.gitrepo.data.repo.contract.IGithubGateway
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubGateway @Inject constructor(
    private val githubApi: GithubApi
): IGithubGateway {

    override fun getSquareRepos(pageNum: Int, size: Int) =
        githubApi.getSquareRepos(pageNum, size)
}