package com.learning.recipe.data

import com.learning.recipe.data.database.RecipesDao
import com.learning.recipe.data.database.entities.FavoritesEntity
import com.learning.recipe.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes() = recipesDao.readRecipes()

    fun readFavoriteRecipes() = recipesDao.readFavoriteRecipes()

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    fun deleteAllRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }
}