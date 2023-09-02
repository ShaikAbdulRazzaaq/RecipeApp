package com.learning.recipeapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.learning.recipeapp.utils.Constants.API_KEY
import com.learning.recipeapp.utils.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.learning.recipeapp.utils.Constants.QUERY_API_KEY
import com.learning.recipeapp.utils.Constants.QUERY_DIET
import com.learning.recipeapp.utils.Constants.QUERY_FILL_INGREDIENTS
import com.learning.recipeapp.utils.Constants.QUERY_NUMBER
import com.learning.recipeapp.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    fun applyQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}