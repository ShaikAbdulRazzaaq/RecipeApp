package com.learning.recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipe.databinding.ItemRecipeBinding
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    private var recipes = emptyList<RecipeResult.Result>()

    class RecipesViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RecipeResult.Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = RecipesViewHolder(
                ItemRecipeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecipesViewHolder.from(parent)

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    fun setData(newData: RecipeResult) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}