package com.romariomkk.gitrepo.domain.usecase

import com.romariomkk.gitrepo.data.repo.contract.IGithubGateway
import com.romariomkk.gitrepo.domain.usecase.contract.IGetSquareReposUseCase
import com.romariomkk.gitrepo.util.PageParams
import com.romariomkk.gitrepo.util.toGithubRepo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetSquareReposUseCase @Inject constructor(
    private val githubGateway: IGithubGateway
) : IGetSquareReposUseCase {

    override fun execute(pageParams: PageParams) =
        githubGateway.getSquareRepos(pageParams.pageNum, pageParams.size)
            .map { serverRepos -> serverRepos.map { it.toGithubRepo() } }
            .subscribeOn(Schedulers.io())
}