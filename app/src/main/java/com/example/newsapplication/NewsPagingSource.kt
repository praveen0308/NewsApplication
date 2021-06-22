package com.example.newsapplication

import androidx.paging.PagingSource
import com.example.newsapplication.retrofit.Article
import com.example.newsapplication.retrofit.NewsApi
import retrofit2.HttpException
import java.io.IOException
import java.io.PrintStream

private const val STARTING_PAGE_INDEX = 1
class NewsPagingSource(
    private val newsApi: NewsApi,
    private val countryName: String
):PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = newsApi.getTopHeadLines(countryName,Constants.API_KEY,position,params.loadSize)
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}