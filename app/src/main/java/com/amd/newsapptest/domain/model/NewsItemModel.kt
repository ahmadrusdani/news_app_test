package com.amd.newsapptest.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsItemModel(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("contributorAvatar")
	val contributorAvatar: String? = null,

	@field:SerializedName("contentThumbnail")
	val contentThumbnail: String? = null,

	@field:SerializedName("slideshow")
	val slideshow: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("contributorName")
	val contributorName: String? = null,

	@field:SerializedName("content")
	val content: String? = null
) : Parcelable
