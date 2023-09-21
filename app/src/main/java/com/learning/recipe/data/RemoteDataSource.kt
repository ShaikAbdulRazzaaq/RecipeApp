package com.learning.recipe.data

import com.learning.recipe.data.network.RecipeApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeApiService: RecipeApiService) {
    suspend fun getRecipes(queries: Map<String, String>) = recipeApiService.getRecipes(queries)

    suspend fun searchRecipes(searchQuery: Map<String, String>) =
        recipeApiService.searchRecipes(searchQuery)

    suspend fun getFoodJoke(apiKey: String) = recipeApiService.getRandomFoodJoke(apiKey)
}