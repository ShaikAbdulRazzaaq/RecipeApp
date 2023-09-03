package com.learning.recipeapp.data

import com.learning.recipeapp.data.database.RecipesDao
import com.learning.recipeapp.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readDatabase() = recipesDao.readRecipes()
}