package com.amd.newsapptest.domain.repository

import com.amd.newsapptest.domain.model.NewsItemModel
import com.amd.newsapptest.domain.model.Resource

interface ArticleRepository {
    suspend fun getArticles() : Resource<List<NewsItemModel>>
}