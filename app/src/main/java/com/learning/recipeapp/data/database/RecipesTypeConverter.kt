package com.learning.recipeapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learning.recipeapp.models.RecipeOnSearchResult

class RecipesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun recipeObjectToJsonString(result: RecipeOnSearchResult): String = gson.toJson(result)

    @TypeConverter
    fun jSonStringToRecipeObject(data: String): RecipeOnSearchResult {
        val typeList = object : TypeToken<RecipeOnSearchResult>() {}.type
        return gson.fromJson(data, typeList)
    }

}