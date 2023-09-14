package com.learning.recipe.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learning.recipe.data.DataStoreRepository
import com.learning.recipe.utils.Constants.API_KEY
import com.learning.recipe.utils.Constants.DEFAULT_DIET_TYPE
import com.learning.recipe.utils.Constants.DEFAULT_MEAL_TYPE
import com.learning.recipe.utils.Constants.DEFAULT_RECIPES_NUMBER
import com.learning.recipe.utils.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.learning.recipe.utils.Constants.QUERY_API_KEY
import com.learning.recipe.utils.Constants.QUERY_DIET
import com.learning.recipe.utils.Constants.QUERY_FILL_INGREDIENTS
import com.learning.recipe.utils.Constants.QUERY_NUMBER
import com.learning.recipe.utils.Constants.QUERY_SEARCH
import com.learning.recipe.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val application: Application, private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    val readDietAndMealType = dataStoreRepository.readMealAndDietType

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readBackOnline =
        dataStoreRepository.readBackOnline.asLiveData(context = viewModelScope.coroutineContext)

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveBackOnline(backOnline)
    }

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(dietType, dietTypeId, mealType, mealTypeId)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()

        viewModelScope.launch {
            readDietAndMealType.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        when {
            !networkStatus -> {
                Toast.makeText(
                    application.applicationContext, "No Internet Connection", Toast.LENGTH_SHORT
                ).show()
                saveBackOnline(true)
            }

            backOnline -> {
                Toast.makeText(
                    application.applicationContext, "We are back online", Toast.LENGTH_SHORT
                ).show()
                saveBackOnline(false)
            }
        }
    }
}