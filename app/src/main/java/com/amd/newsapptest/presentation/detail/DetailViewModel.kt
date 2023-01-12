package com.amd.newsapptest.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.newsapptest.domain.model.NewsItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _articlesState = MutableStateFlow<NewsItemModel?>(null)
    val articlesState = _articlesState.asStateFlow()

    init {
        getInitData()
    }

    private fun getInitData() = viewModelScope.launch {
        _articlesState.emit(savedStateHandle.get<NewsItemModel?>("articles"))
    }

}