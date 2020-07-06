package com.romariomkk.gitrepo.di.module

import com.romariomkk.gitrepo.data.repo.GithubGateway
import com.romariomkk.gitrepo.data.repo.contract.IGithubGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module(includes = [
    NetworkModule::class])
interface RepositoryModule {

    @Binds
    fun bindGithubRepo(repo: GithubGateway): IGithubGateway
}