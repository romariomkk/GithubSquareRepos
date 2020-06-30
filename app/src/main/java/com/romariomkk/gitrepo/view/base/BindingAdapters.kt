package com.romariomkk.gitrepo.view.base

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.VectorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.romariomkk.gitrepo.R
import com.romariomkk.gitrepo.config.GlideApp
import com.romariomkk.gitrepo.util.Utils


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("languageTint")
    fun bindDrawableColor(tv: TextView, language: String?) {
        language?.let {
            tv.isVisible = true
            val context = tv.context

            val langDrawable = (context.getDrawable(R.drawable.ic_dot)!!.mutate() as VectorDrawable)
                .apply {
                    setTint(
                        ContextCompat.getColor(
                            context, Utils.LANGUAGE_COLOR_MAP[language] ?: R.color.colorPrimary)
                    )
                }

            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(langDrawable, null, null, null)
        } ?: run {
            tv.isVisible = false
        }
    }

    @JvmStatic
    @BindingAdapter("repoUrl")
    fun bindRepoImageUrl(iv: ImageView, url: String) {
        GlideApp.with(iv.context)
            .load(url)
            .apply(RequestOptions.noTransformation())
            .into(iv)
    }

}