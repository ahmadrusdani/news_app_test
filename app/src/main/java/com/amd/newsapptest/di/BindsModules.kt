package com.amd.newsapptest.di

import com.amd.newsapptest.data.repository.ArticlesRepositoryImpl
import com.amd.newsapptest.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModules {

    @Binds
    abstract fun provideArticlesRepository(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticleRepository

}