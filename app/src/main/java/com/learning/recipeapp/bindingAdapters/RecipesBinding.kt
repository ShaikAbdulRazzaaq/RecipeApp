package com.learning.recipeapp.bindingAdapters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.learning.recipeapp.data.database.RecipesEntity
import com.learning.recipeapp.data.network.NetworkResult
import com.learning.recipeapp.models.RecipeResult

class RecipesBinding {
    companion object {
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun imageViewVisibility(
            imageView: AppCompatImageView,
            apiResponse: NetworkResult<RecipeResult>?,
            database: List<RecipesEntity>?
        ) {
            when {
                apiResponse is NetworkResult.Error && database.isNullOrEmpty() -> imageView.visibility =
                    View.VISIBLE

                apiResponse is NetworkResult.Loading -> imageView.visibility = View.GONE
                apiResponse is NetworkResult.Success -> imageView.visibility = View.GONE
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: MaterialTextView,
            apiResponse: NetworkResult<RecipeResult>?,
            database: List<RecipesEntity>?
        ) {
            when {
                apiResponse is NetworkResult.Error && database.isNullOrEmpty() -> {
                    textView.visibility = View.VISIBLE
                    textView.text = apiResponse.message.toString()
                }

                apiResponse is NetworkResult.Loading -> textView.visibility = View.GONE
                apiResponse is NetworkResult.Success -> textView.visibility = View.GONE
            }
        }
    }
}