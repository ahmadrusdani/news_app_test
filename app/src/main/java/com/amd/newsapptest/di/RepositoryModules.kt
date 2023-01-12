package com.amd.newsapptest.di

import com.amd.newsapptest.data.data_source.ApiService
import com.amd.newsapptest.data.repository.ArticlesRepositoryImpl
import com.amd.newsapptest.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModules {

    @Singleton
    @Provides
    internal fun provideArticlesRepository(
        apiService: ApiService
    ): ArticlesRepositoryImpl {
        return ArticlesRepositoryImpl(apiService)
    }

}