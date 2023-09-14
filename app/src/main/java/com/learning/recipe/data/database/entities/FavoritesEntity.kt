package com.learning.recipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(@PrimaryKey(autoGenerate = true) var id: Int,val result: RecipeResult.Result)