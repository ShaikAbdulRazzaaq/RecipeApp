package com.learning.recipeapp.utils

object Constants {
    const val API_KEY = "a5934ae043d04120b0d1e4396bc11138"
    const val BASE_URL = "https://api.spoonacular.com/"

    //Query Keys
    const val QUERY_SEARCH = "query"
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"
    const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"

    //Room Database
    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"

    //Bottom Sheet and Preferences
    const val PREFERENCES_NAME = "recipesPreferences"
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val DEFAULT_RECIPES_NUMBER = "50"
    const val PREFERENCES_MEAL_TYPE_KEY = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID_KEY = "mealTypeId"
    const val PREFERENCES_DIET_TYPE_KEY = "dietType"
    const val PREFERENCES_DIET_TYPE_ID_KEY = "dietTypeId"
    const val PREFERENCES_BACK_ONLINE_KEY = "backOnline"
}
