package com.learning.recipe.bindingAdapters

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipe.adapters.FavoritesRecipeAdapter
import com.learning.recipe.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        fun setDataVisibility(
            viewGroup: ViewGroup,
            favoritesEntities: List<FavoritesEntity>?,
            adapter: FavoritesRecipeAdapter
        ) {
            viewGroup.visibility = if (favoritesEntities.isNullOrEmpty()) {
                when (viewGroup) {
                    is ConstraintLayout -> View.VISIBLE
                    is RecyclerView -> View.GONE
                    else -> viewGroup.visibility
                }
            } else {
                when (viewGroup) {
                    is ConstraintLayout -> View.GONE
                    is RecyclerView -> {
                        favoritesEntities.let {
                            adapter.setData(it)
                        }
                        View.VISIBLE
                    }

                    else -> viewGroup.visibility
                }
            }
        }
    }
}