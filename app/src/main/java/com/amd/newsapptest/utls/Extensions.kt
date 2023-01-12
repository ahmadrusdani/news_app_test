package com.amd.newsapptest.utls

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.amd.newsapptest.app.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat

object Extensions {

    fun isValidContextForGlide(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            val activity = context as Activity?
            if (activity!!.isDestroyed || activity.isFinishing) {
                return false
            }
        }
        return true
    }

    fun ImageView.load(strImage: String, isCenterCrop: Boolean = true) {
//        if (isValidContextForGlide(this.context)) {
        if (isCenterCrop) {
            GlideApp.with(this.context)
                .asDrawable()
                .load(strImage)
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(this)
        } else {
            GlideApp.with(this.context)
                .asDrawable()
                .load(strImage)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(this)
        }
//        }
    }

    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun String?.formatDate(pattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): String? {
        return try {
            val dateFormat = SimpleDateFormat(pattern).parse(this)
            dateFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy").format(it) }
        } catch (e: Exception) {
            println(e.message)
            ""
        }
    }

    fun RecyclerView.addDivider(@DrawableRes drawable: Int, type: Int = LinearLayout.VERTICAL) =
        this.addItemDecoration(
            DividerItemDecoration(
                this.context,
                type
            ).also {
                it.setDrawable(
                    this.context.getDrawableCompact(
                        drawable
                    )!!
                )
            })

    fun Context.getDrawableCompact(int: Int) = ContextCompat.getDrawable(this, int)

}