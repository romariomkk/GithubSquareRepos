package com.romariomkk.gitrepo.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.romariomkk.gitrepo.GithubApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [
    RepositoryModule::class,
    UseCaseModule::class])
internal class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApp(application: Application): GithubApp = application as GithubApp

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}
