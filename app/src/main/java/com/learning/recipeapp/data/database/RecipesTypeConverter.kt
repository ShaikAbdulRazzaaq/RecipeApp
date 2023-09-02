package com.learning.recipeapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learning.recipeapp.models.RecipeResult

class RecipesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun recipeObjectToJsonString(result: RecipeResult): String = gson.toJson(result)

    @TypeConverter
    fun jSonStringToRecipeObject(data: String): RecipeResult {
        val typeList = object : TypeToken<RecipeResult>() {}.type
        return gson.fromJson(data, typeList)
    }

}