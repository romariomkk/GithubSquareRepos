package com.romariomkk.gitrepo.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.romariomkk.gitrepo.domain.pojo.GithubRepo
import com.romariomkk.gitrepo.data.pojo.ServerGithubRepo
import com.romariomkk.gitrepo.data.repo.contract.IGithubGateway
import com.romariomkk.gitrepo.domain.usecase.contract.IGetSquareReposUseCase
import com.romariomkk.gitrepo.util.PageParams
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetSquareReposUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val githubGateway = mock<IGithubGateway>()
    private lateinit var getSquareReposUseCase: IGetSquareReposUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        getSquareReposUseCase = GetSquareReposUseCase(githubGateway)
    }

    @Test
    fun `test successful retrieve of a page`() {
        val serverResponse = emptyList<ServerGithubRepo>()
        val response = emptyList<GithubRepo>()
        val arg = PageParams()

        Mockito.`when`(githubGateway.getSquareRepos(arg.pageNum, arg.size))
            .thenReturn(Single.just(serverResponse))

        getSquareReposUseCase.execute(arg)
            .test()
            .await()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue(response)
            .assertComplete()
            .dispose()
    }

    @Test
    fun `test erroneous retrieve of a page`() {
        val response = Throwable("Error")
        val arg = PageParams()

        Mockito.`when`(githubGateway.getSquareRepos(arg.pageNum, arg.size))
            .thenReturn(Single.error(response))

        getSquareReposUseCase.execute(arg)
            .test()
            .await()
            .assertSubscribed()
            .assertError(response )
            .assertNotComplete()
            .dispose()
    }
}