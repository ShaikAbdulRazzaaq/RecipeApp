package com.learning.recipeapp.bindingAdapters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.learning.recipeapp.R

class RecipeItemBinding {
    companion object {

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: AppCompatImageView, imageUrl: String?) {
            imageUrl?.let {
                imageView.load(it) {
                    crossfade(600)
                    error(R.drawable.error_drawable)
                }
            }
        }

        @BindingAdapter("setSummary")
        @JvmStatic
        fun setSummary(textView: MaterialTextView, summary: String) {
            textView.text = buildSpannedString {
                append(HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_COMPACT))
            }
        }

        @BindingAdapter("likes")
        @JvmStatic
        fun setNumberOfLikes(textView: MaterialTextView, likes: Int?) {
            textView.text = likes?.toString()
        }

        @BindingAdapter("cookingTime")
        @JvmStatic
        fun cookingTime(textView: MaterialTextView, time: Int?) {
            textView.text = time?.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is MaterialTextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context, R.color.jungle_green
                            )
                        )
                    }

                    is AppCompatImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context, R.color.jungle_green
                            )
                        )
                    }
                }
            }
        }
    }
}