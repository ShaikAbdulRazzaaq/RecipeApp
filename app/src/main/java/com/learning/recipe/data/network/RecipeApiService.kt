package com.learning.recipe.data.network

import com.learning.recipe.models.RandomFoodJokeResponse
import com.learning.recipe.models.RecipeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeApiService {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<RecipeResult>

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap searchQuery: Map<String, String>): Response<RecipeResult>

    @GET("food/jokes/random")
    suspend fun getRandomFoodJoke(@Query("apiKey") apiKey: String): Response<RandomFoodJokeResponse>

}