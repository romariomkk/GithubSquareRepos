package com.romariomkk.gitrepo.domain.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.romariomkk.gitrepo.domain.api.GithubApi
import com.romariomkk.gitrepo.domain.pojo.git.server.ServerGithubRepo
import com.romariomkk.gitrepo.domain.repo.contract.IGithubGateway
import com.romariomkk.gitrepo.util.PageParams
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GithubGatewayTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val githubApi = mock<GithubApi>()

    private lateinit var githubGateway: IGithubGateway

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        githubGateway = GithubGateway(githubApi)
    }

    @Test
    fun `get repos empty`() {
        val response = emptyList<ServerGithubRepo>()
        val arg = PageParams()
        Mockito.`when`(githubApi.getSquareRepos(arg.pageNum, arg.size)).thenReturn(Single.just(response))

        githubGateway.getSquareRepos(arg.pageNum, arg.size)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { list -> list.isEmpty() }
            .assertValue(response)
            .assertComplete()
            .dispose()
    }

    @Test
    fun `get repos non empty`() {
        val mockRepo = mock<ServerGithubRepo>()
        val response = listOf(mockRepo)
        val arg = PageParams()
        Mockito.`when`(githubApi.getSquareRepos(arg.pageNum, arg.size)).thenReturn(Single.just(response))

        githubGateway.getSquareRepos(arg.pageNum, arg.size)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { list -> list.isNotEmpty() }
            .assertValue(response)
            .assertComplete()
            .dispose()
    }

    @Test
    fun `get repos error`() {
        val response = Throwable("Error")
        val arg = PageParams()
        Mockito.`when`(githubApi.getSquareRepos(arg.pageNum, arg.size)).thenReturn(Single.error(response))

        githubGateway.getSquareRepos(arg.pageNum, arg.size)
            .test()
            .assertSubscribed()
            .assertError(response)
            .assertNotComplete()
            .dispose()
    }
}