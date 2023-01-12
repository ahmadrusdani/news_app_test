package com.amd.newsapptest.data.data_source

import com.amd.newsapptest.domain.model.NewsItemModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("articles")
    suspend fun getArticles(): Response<List<NewsItemModel>>

}