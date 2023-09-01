package com.learning.recipeapp.data

import com.learning.recipeapp.data.network.RecipeApiService
import com.learning.recipeapp.models.RecipeOnSearchResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeApiService: RecipeApiService) {
    suspend fun getRecipes(queries: Map<String, String>): Response<RecipeOnSearchResult> =
        recipeApiService.getRecipes(queries)
}