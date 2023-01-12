package com.amd.newsapptest.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amd.newsapptest.R
import com.amd.newsapptest.databinding.ActivityDetailBinding
import com.amd.newsapptest.presentation.adapter.ArticlesPhotoAdapter
import com.amd.newsapptest.utls.Extensions.addDivider
import com.amd.newsapptest.utls.Extensions.formatDate
import com.amd.newsapptest.utls.Extensions.load
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    private val photoAdapter by lazy {
        ArticlesPhotoAdapter(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setupView()
        setObserver()
    }

    private fun setupView() = with(binding) {
        rvPhoto.apply {
            adapter = photoAdapter
            addDivider(R.drawable.horizontal_space, LinearLayout.HORIZONTAL)
        }
    }

    private fun setObserver() = with(binding) {
        detailViewModel.articlesState.onEach {
            it?.let { data ->
                txtTitle.text = data.title
                imgArticles.load(data.contentThumbnail.toString())
                txtPublisher.text = data.contributorName
                txtDate.text = data.createdAt.formatDate()
                rvPhoto.isVisible = !data.slideshow.isNullOrEmpty()
                photoAdapter.differ.submitList(data.slideshow)
                txtDesc.text = data.content
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}