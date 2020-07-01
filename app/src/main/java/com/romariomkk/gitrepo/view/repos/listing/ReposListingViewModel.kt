package com.romariomkk.gitrepo.view.repos.listing

import androidx.hilt.lifecycle.ViewModelInject
import com.romariomkk.gitrepo.domain.pojo.git.app.GithubRepo
import com.romariomkk.gitrepo.domain.usecase.contract.IGetSquareReposUseCase
import com.romariomkk.gitrepo.util.PageParams
import com.romariomkk.gitrepo.util.ResourceListLiveData
import com.romariomkk.gitrepo.view.base.AbsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import timber.log.Timber

class ReposListingViewModel @ViewModelInject constructor(
    private val squareReposUseCase: IGetSquareReposUseCase
) : AbsViewModel() {

    val squareRepoSource = ResourceListLiveData<GithubRepo>()
    private var params = PageParams()

    override fun onAttached() {
        loadInitial()
    }

    fun loadInitial() {
        squareReposUseCase.execute(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                squareRepoSource.set(it, it.size == params.size, true)
            }, {
                Timber.e("$it")
                squareRepoSource.initialError(it)
            })
            .addTo(disposables)
    }

    fun loadNext() {
        if (!squareRepoSource.hasMore)
            return

        params = params.nextPage()

        squareReposUseCase.execute(params)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { squareRepoSource.loading() }
            .subscribe({
                squareRepoSource.addAll(it, it.size == params.size)
            }, {
                squareRepoSource.error(it)
            })
            .addTo(disposables)
    }

}