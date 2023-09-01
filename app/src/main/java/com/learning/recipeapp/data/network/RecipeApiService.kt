package com.learning.recipeapp.data.network

import com.learning.recipeapp.models.RecipeOnSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipeApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<RecipeOnSearchResult>
}