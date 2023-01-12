package com.amd.newsapptest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amd.newsapptest.databinding.ItemCardPhotoBinding
import com.amd.newsapptest.databinding.ItemPhotoSliderBinding
import com.amd.newsapptest.utls.Extensions.load

class ArticlesPhotoAdapter(private val type: Int = 0) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPhotoSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(item: String) = with(binding) {
            imgArticles.load(item)
        }

    }

    inner class ViewHolderCard(val binding: ItemCardPhotoBinding): RecyclerView.ViewHolder(binding.root) {

        fun binds(item: String) = with(binding) {
            imgArticles.load(item)
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (type > 0) {
            return ViewHolderCard(
                ItemCardPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        return ViewHolder(
            ItemPhotoSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binds(differ.currentList[holder.adapterPosition])
        } else {
            (holder as ViewHolderCard).binds(differ.currentList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}