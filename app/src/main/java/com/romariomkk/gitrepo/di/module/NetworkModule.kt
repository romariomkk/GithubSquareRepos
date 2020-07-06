package com.romariomkk.gitrepo.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.romariomkk.gitrepo.BuildConfig
import com.romariomkk.gitrepo.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
open class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        stethoInterceptor: StethoInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
        with(builder) {

            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(stethoInterceptor)
                addNetworkInterceptor(httpLoggingInterceptor)
            }

            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): GithubApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubApi::class.java)

    @Provides
    @Singleton
    fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()
}