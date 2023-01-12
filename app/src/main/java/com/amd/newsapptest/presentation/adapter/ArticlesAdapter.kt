package com.amd.newsapptest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.amd.newsapptest.databinding.ItemArticlesBinding
import com.amd.newsapptest.databinding.ItemArticlesMultithumbBinding
import com.amd.newsapptest.domain.model.NewsItemModel
import com.amd.newsapptest.utls.Extensions.formatDate
import com.amd.newsapptest.utls.Extensions.load

class ArticlesAdapter(
    private val onClick: ((NewsItemModel) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val single = 1
    private val multi = 2

    inner class ViewHolder(val binding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(item: NewsItemModel) = with(binding) {
            imgArticles.load(item.contentThumbnail.toString())
            txtPublisher.text = item.contributorName.toString()
            txtTitle.text = item.title.toString()
            txtDate.text = item.createdAt?.formatDate()
            root.setOnClickListener {
                onClick?.invoke(item)
            }
        }

    }

    inner class ViewHolderMultiThumb(val binding: ItemArticlesMultithumbBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(item: NewsItemModel) = with(binding) {
            val adapterPhoto = ArticlesPhotoAdapter()
            rvPhoto.apply {
                adapter = adapterPhoto
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val position = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        indicator.selection = position
                    }
                })
            }
            adapterPhoto.differ.submitList(item.slideshow)
            binding.indicator.count = adapterPhoto.itemCount
            txtPublisher.text = item.contributorName.toString()
            txtTitle.text = item.title.toString()
            txtDate.text = item.createdAt?.formatDate()
            root.setOnClickListener {
                onClick?.invoke(item)
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<NewsItemModel>() {
        override fun areItemsTheSame(oldItem: NewsItemModel, newItem: NewsItemModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsItemModel,
            newItem: NewsItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == single) {
            return ViewHolder(
                ItemArticlesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        return ViewHolderMultiThumb(
            ItemArticlesMultithumbBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binds(differ.currentList[holder.adapterPosition])
        } else {
            (holder as ViewHolderMultiThumb).binds(differ.currentList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position].slideshow?.isNotEmpty() == true) multi else single
    }
}