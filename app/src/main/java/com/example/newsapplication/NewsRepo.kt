package com.example.newsapplication

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapplication.retrofit.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepo @Inject constructor(private val newsApi: NewsApi) {

    fun getNewsHeadlines(country : String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {NewsPagingSource(newsApi,country)}
        ).liveData
}