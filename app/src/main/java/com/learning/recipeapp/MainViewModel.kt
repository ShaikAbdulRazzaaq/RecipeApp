package com.learning.recipeapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.learning.recipeapp.data.NetworkResult
import com.learning.recipeapp.data.Repository
import com.learning.recipeapp.models.RecipeOnSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application, private val repository: Repository
) : AndroidViewModel(application) {

    val recipesResponse: MutableLiveData<NetworkResult<RecipeOnSearchResult>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value=NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remoteDataSource.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                recipesResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            recipesResponse.value = NetworkResult.Error(error = "No Internet Connection")
        }}

    private fun handleFoodRecipesResponse(response: Response<RecipeOnSearchResult>): NetworkResult<RecipeOnSearchResult> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")

            response.code() == 402 -> NetworkResult.Error("API Key Limited.")

            response.body()?.results.isNullOrEmpty() -> NetworkResult.Error("No Recipes found.")

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