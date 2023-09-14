package com.learning.recipe.data

import com.learning.recipe.data.network.RecipeApiService
import com.learning.recipe.models.RecipeResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeApiService: RecipeApiService) {
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipeResult> =
        recipeApiService.getRecipes(queries)

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<RecipeResult> =
        recipeApiService.searchRecipes(searchQuery)
}