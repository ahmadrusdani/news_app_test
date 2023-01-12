package com.amd.newsapptest.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.newsapptest.domain.model.NewsItemModel
import com.amd.newsapptest.domain.repository.ArticleRepository
import com.amd.newsapptest.utls.check
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articlesState = MutableStateFlow<List<NewsItemModel>>(emptyList())
    private val _loadingState = MutableStateFlow(false)
    private val _failureState = Channel<Throwable>()

    val articleState = _articlesState.asStateFlow()
    val loadingState = _loadingState.asStateFlow()
    val failureState = _failureState.consumeAsFlow()

    init {
        getArticles()
    }

    fun getArticles() = viewModelScope.launch(Dispatchers.IO) {
        _loadingState.emit(true)
        articleRepository.getArticles().check(
            success = { response ->
                _loadingState.emit(false)
                _articlesState.emit(response)
            },
            failure = { err ->
                _loadingState.emit(false)
                _failureState.send(err)
            }
        )
    }

}