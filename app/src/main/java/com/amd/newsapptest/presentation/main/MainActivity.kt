package com.amd.newsapptest.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amd.newsapptest.R
import com.amd.newsapptest.databinding.ActivityMainBinding
import com.amd.newsapptest.presentation.adapter.ArticlesAdapter
import com.amd.newsapptest.presentation.detail.DetailActivity
import com.amd.newsapptest.utls.Extensions.addDivider
import com.amd.newsapptest.utls.Extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    private val articlesAdapter by lazy {
        ArticlesAdapter { item ->
            startActivity(Intent(this, DetailActivity::class.java).also {
                it.putExtra("articles", item)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setupView()
        setupObserver()
    }

    private fun setupObserver() = with(mainViewModel) {
        articleState.onEach {
            binding.rvArticles.isVisible = it.isNotEmpty()
            articlesAdapter.differ.submitList(it)
        }.launchIn(lifecycleScope)
        loadingState.onEach {
            if (it) {
                binding.layoutEmpty.root.isVisible = false
            }
            binding.progressBar.isVisible = it
        }.launchIn(lifecycleScope)
        failureState.onEach {
            binding.layoutEmpty.root.isVisible = true
            this@MainActivity.showToast("Terjadi kesalahan")
        }.launchIn(lifecycleScope)
    }

    private fun setupView() = with(binding) {
        rvArticles.adapter = articlesAdapter
        rvArticles.addDivider(R.drawable.horizontal_space, LinearLayout.VERTICAL)
        layoutEmpty.btnAction.setOnClickListener {
            mainViewModel.getArticles()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}