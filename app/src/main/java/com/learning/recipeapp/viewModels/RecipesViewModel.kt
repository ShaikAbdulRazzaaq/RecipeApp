package com.learning.recipeapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.learning.recipeapp.data.DataStoreRepository
import com.learning.recipeapp.utils.Constants.API_KEY
import com.learning.recipeapp.utils.Constants.DEFAULT_DIET_TYPE
import com.learning.recipeapp.utils.Constants.DEFAULT_MEAL_TYPE
import com.learning.recipeapp.utils.Constants.DEFAULT_RECIPES_NUMBER
import com.learning.recipeapp.utils.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.learning.recipeapp.utils.Constants.QUERY_API_KEY
import com.learning.recipeapp.utils.Constants.QUERY_DIET
import com.learning.recipeapp.utils.Constants.QUERY_FILL_INGREDIENTS
import com.learning.recipeapp.utils.Constants.QUERY_NUMBER
import com.learning.recipeapp.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application, private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private val readDietAndMealType = dataStoreRepository.readMealAndDietType

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

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
}