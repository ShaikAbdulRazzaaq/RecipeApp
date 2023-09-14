package com.learning.recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.learning.recipe.R
import com.learning.recipe.databinding.ItemIngredientsBinding
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants.BASE_IMAGE_URL
import com.learning.recipe.utils.RecipesDiffUtil
import java.util.Locale

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    class IngredientsViewHolder(val binding: ItemIngredientsBinding) : ViewHolder(binding.root)

    private var ingredientsList = emptyList<RecipeResult.Result.ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IngredientsViewHolder(
        ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = ingredientsList.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.binding.apply {
            ingredient.apply {
                appCompatImageView2.load(buildString {
                    append(BASE_IMAGE_URL)
                    append(ingredient.image)
                }) {
                    crossfade(600)
                    error(R.drawable.error_drawable)
                }
                tvIngredientName.text = name?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                tvIngredientAmount.text = amount?.toString()
                tvIngredientUnit.text = unit
                tvIngredientConsistency.text = consistency
                tvIngredientOriginal.text = original
            }
        }
    }

    fun setData(newIngredients: List<RecipeResult.Result.ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(newIngredients, ingredientsList)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}