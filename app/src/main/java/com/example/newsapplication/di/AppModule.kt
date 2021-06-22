package com.example.newsapplication.di

import com.example.newsapplication.Constants
import com.example.newsapplication.NewsRepo
import com.example.newsapplication.retrofit.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit):NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepo(newsApi: NewsApi):NewsRepo=
        NewsRepo(newsApi)
}