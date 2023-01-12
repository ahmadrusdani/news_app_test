package com.amd.newsapptest.data.repository

import com.amd.newsapptest.data.data_source.ApiService
import com.amd.newsapptest.domain.model.NewsItemModel
import com.amd.newsapptest.domain.model.Resource
import com.amd.newsapptest.domain.repository.ArticleRepository

class ArticlesRepositoryImpl(
    private val apiService: ApiService
): ArticleRepository, BaseRepository() {

    override suspend fun getArticles(): Resource<List<NewsItemModel>> {
        return safeApiCall { apiService.getArticles() }
    }
}