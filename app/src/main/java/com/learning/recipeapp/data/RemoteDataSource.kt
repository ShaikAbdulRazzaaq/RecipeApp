package com.learning.recipeapp.data

import com.learning.recipeapp.data.network.RecipeApiService
import com.learning.recipeapp.models.RecipeResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeApiService: RecipeApiService) {
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipeResult> =
        recipeApiService.getRecipes(queries)

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<RecipeResult> =
        recipeApiService.searchRecipes(searchQuery)
}