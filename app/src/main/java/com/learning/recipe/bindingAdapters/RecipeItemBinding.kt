package com.learning.recipe.bindingAdapters

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.learning.recipe.R
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.ui.fragments.recipe.RecipeFragmentDirections

class RecipeItemBinding {
    companion object {
        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(cardView: MaterialCardView, result: RecipeResult.Result) {
            cardView.setOnClickListener {
                kotlin.runCatching {
                    cardView.findNavController().navigate(
                        RecipeFragmentDirections.actionRecipeFragmentToDetailsActivity(result)
                    )
                }.onFailure {
                    Log.e(TAG, "onRecipeClickListener:${it.message} ", it)
                }
            }
        }

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
//              Using HTMLCompat Library of Android
            textView.text = buildSpannedString {
                append(HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_COMPACT))
            }

            //Using Jsoup
//            textView.text = Jsoup.parse(summary).text()
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

        private const val TAG = "RecipeItemBinding"
    }

}