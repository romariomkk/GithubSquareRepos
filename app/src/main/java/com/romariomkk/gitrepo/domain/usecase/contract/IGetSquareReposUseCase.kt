package com.romariomkk.gitrepo.domain.usecase.contract

import com.romariomkk.gitrepo.domain.pojo.GithubRepo
import com.romariomkk.gitrepo.util.PageParams
import io.reactivex.Single

interface IGetSquareReposUseCase {
    fun execute(pageParams: PageParams): Single<List<GithubRepo>>
}