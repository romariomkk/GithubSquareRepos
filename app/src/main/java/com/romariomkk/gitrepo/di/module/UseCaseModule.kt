package com.romariomkk.gitrepo.di.module

import com.romariomkk.gitrepo.domain.usecase.GetSquareReposUseCase
import com.romariomkk.gitrepo.domain.usecase.contract.IGetSquareReposUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface UseCaseModule {

    @Binds
    fun bindGetSquareUseCase(getSquareReposUseCase: GetSquareReposUseCase): IGetSquareReposUseCase
}