package com.learning.recipe.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learning.recipe.data.Repository
import com.learning.recipe.data.database.entities.FavoritesEntity
import com.learning.recipe.data.database.entities.FoodJokeEntity
import com.learning.recipe.data.database.entities.RecipesEntity
import com.learning.recipe.data.network.NetworkResult
import com.learning.recipe.models.RandomFoodJokeResponse
import com.learning.recipe.models.RecipeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application, private val repository: Repository
) : AndroidViewModel(application) {
    /** ROOM DATABASE */
    val readDatabase = repository.localDataSource.readRecipes()
        .asLiveData(context = viewModelScope.coroutineContext)

    val readFavoriteRecipes = repository.localDataSource.readFavoriteRecipes()
        .asLiveData(context = viewModelScope.coroutineContext)

    val readFoodJoke = repository.localDataSource.readFoodJoke()
        .asLiveData(context = viewModelScope.coroutineContext)

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localDataSource.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localDataSource.insertFavoriteRecipes(favoritesEntity)
        }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.localDataSource.insertFoodJoke(foodJokeEntity)
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localDataSource.deleteFavoriteRecipe(favoritesEntity)
        }

    /** RETROFIT */
    val recipesResponse: MutableLiveData<NetworkResult<RecipeResult>> = MutableLiveData()
    val searchedRecipesResponse: MutableLiveData<NetworkResult<RecipeResult>> = MutableLiveData()
    val foodJokeResponse: MutableLiveData<NetworkResult<RandomFoodJokeResponse>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDataSource.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)
                foodJokeResponse.value?.data?.let {
                    offLineCacheFoodJoke(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                foodJokeResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error(error = "No Internet Connection")
        }
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDataSource.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                recipesResponse.value?.data?.let {
                    offLineCacheRecipe(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                recipesResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            recipesResponse.value = NetworkResult.Error(error = "No Internet Connection")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDataSource.searchRecipes(searchQuery)
                searchedRecipesResponse.value = handleFoodRecipesResponse(response)

            } catch (e: Exception) {
                e.printStackTrace()
                searchedRecipesResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            searchedRecipesResponse.value = NetworkResult.Error(error = "No Internet Connection")
        }
    }

    private fun offLineCacheRecipe(result: RecipeResult) {
        insertRecipes(RecipesEntity(result))
    }

    private fun offLineCacheFoodJoke(foodJokeResponse: RandomFoodJokeResponse) {
        insertFoodJoke(FoodJokeEntity(foodJokeResponse))
    }

    private fun handleFoodRecipesResponse(response: Response<RecipeResult>): NetworkResult<RecipeResult> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")

            response.code() == 402 -> NetworkResult.Error("API Key Limited.")

            response.body()?.results.isNullOrEmpty() -> NetworkResult.Error("No Recipes found.")

            response.isSuccessful -> NetworkResult.Success(response.body()!!)

            else -> NetworkResult.Error(response.message())
        }
    }

    private fun handleFoodJokeResponse(response: Response<RandomFoodJokeResponse>): NetworkResult<RandomFoodJokeResponse> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")

            response.code() == 402 -> NetworkResult.Error("API Key Limited.")

            response.body()?.text.isNullOrEmpty() -> NetworkResult.Error("No Joke found.")

            response.isSuccessful -> NetworkResult.Success(response.body()!!)

            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}