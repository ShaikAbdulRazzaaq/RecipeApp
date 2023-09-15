package com.learning.recipe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.learning.recipe.data.database.entities.FavoritesEntity
import com.learning.recipe.databinding.ItemFavoriteRecipeBinding
import com.learning.recipe.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.learning.recipe.utils.RecipesDiffUtil

class FavoritesRecipeAdapter :
    RecyclerView.Adapter<FavoritesRecipeAdapter.FavoriteRecipeViewHolderClass>() {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class FavoriteRecipeViewHolderClass(val binding: ItemFavoriteRecipeBinding) :
        ViewHolder(binding.root) {
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = FavoriteRecipeViewHolderClass(
        ItemFavoriteRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = favoriteRecipes.size

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolderClass, position: Int) {
        val favoritesEntity = favoriteRecipes[position]
        holder.bind(favoritesEntity)
        holder.binding.cvRecipe.setOnClickListener {
            holder.itemView.findNavController().navigate(
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                    favoritesEntity.result
                )
            )
        }

    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        Log.d(TAG, "setData: $newFavoriteRecipes")
        val recipesDiffUtil = RecipesDiffUtil(newFavoriteRecipes, favoriteRecipes)
        favoriteRecipes = newFavoriteRecipes
        DiffUtil.calculateDiff(recipesDiffUtil).dispatchUpdatesTo(this)
    }

    companion object {
        private const val TAG = "FavoritesRecipeAdapter"
    }
}