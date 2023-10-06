package com.learning.recipe.data

import com.learning.recipe.data.database.RecipesDao
import com.learning.recipe.data.database.entities.FavoritesEntity
import com.learning.recipe.data.database.entities.FoodJokeEntity
import com.learning.recipe.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes() = recipesDao.readRecipes()

    fun readFavoriteRecipes() = recipesDao.readFavoriteRecipes()

    fun readFoodJoke()=recipesDao.readFoodJoke()

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

}