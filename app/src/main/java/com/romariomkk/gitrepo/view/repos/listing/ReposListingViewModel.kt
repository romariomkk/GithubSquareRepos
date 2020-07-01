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
    private lateinit var params: PageParams

    override fun onAttached() {
        loadInitial()
    }

    fun loadInitial() {
        params = PageParams()
        loadCurrent()
    }

    fun loadNext() {
        if (!squareRepoSource.hasMore)
            return

        loadCurrent()
    }

    fun loadCurrent() {
        squareReposUseCase.execute(params)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (params.pageNum == PageParams.START_PAGE)
                    squareRepoSource.initialLoading()
                else
                    squareRepoSource.loading()
            }
            .subscribe({
                if (params.pageNum == PageParams.START_PAGE)
                    squareRepoSource.set(it, it.size == params.size, true)
                else
                    squareRepoSource.addAll(it, it.size == params.size)

                incPagingArg()
            }, {
                Timber.e("$it")
                if (params.pageNum == PageParams.START_PAGE)
                    squareRepoSource.initialError(it)
                else
                    squareRepoSource.error(it)
            })
            .addTo(disposables)
    }

    private fun incPagingArg() {
        params = params.nextPage()
    }

}