package com.example.newsapplication

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.newsapplication.retrofit.Article

class NewsViewModel @ViewModelInject constructor(
    private val newsRepo: NewsRepo
):ViewModel() {
    val list:LiveData<PagingData<Article>> = newsRepo.getNewsHeadlines("in")
}