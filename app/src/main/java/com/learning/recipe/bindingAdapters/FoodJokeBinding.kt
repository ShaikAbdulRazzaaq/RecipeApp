package com.learning.recipe.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.learning.recipe.data.database.entities.FoodJokeEntity
import com.learning.recipe.data.network.NetworkResult
import com.learning.recipe.models.RandomFoodJokeResponse

class FoodJokeBinding {
    companion object {
        @BindingAdapter("readApiResponse3", "readDatabase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<RandomFoodJokeResponse>?,
            database: List<FoodJokeEntity>?
        ) {
            apiResponse?.let {
                when (apiResponse) {
                    is NetworkResult.Error -> when (view) {
                        is LottieAnimationView -> view.visibility = View.INVISIBLE

                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database.isNullOrEmpty()) {
                                view.visibility = View.INVISIBLE
                            }
                        }
                    }

                    is NetworkResult.Loading -> when (view) {
                        is LottieAnimationView -> view.visibility = View.VISIBLE

                        is MaterialCardView -> view.visibility = View.INVISIBLE
                    }

                    is NetworkResult.Success -> when (view) {
                        is LottieAnimationView -> view.visibility = View.INVISIBLE

                        is MaterialCardView -> view.visibility = View.VISIBLE
                    }
                }
            }
        }

        @BindingAdapter("readApiResponse4", "readDatabase4", requireAll = true)
        @JvmStatic
        fun errorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<RandomFoodJokeResponse>?,
            database: List<FoodJokeEntity>?
        ) {
            if (database != null) {
                if (database.isEmpty()) {
                    view.visibility = View.VISIBLE
                    if (view is MaterialTextView) {
                        apiResponse?.let {
                            view.text = it.message
                        }
                    }
                }
            }
            if (apiResponse is NetworkResult.Success) {
                view.visibility = View.INVISIBLE
            }
        }
    }
}