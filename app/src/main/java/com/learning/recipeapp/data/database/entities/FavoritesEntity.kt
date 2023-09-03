package com.learning.recipeapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipeapp.models.RecipeResult
import com.learning.recipeapp.utils.Constants.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(@PrimaryKey(autoGenerate = true) var id: Int, result: RecipeResult.Result)