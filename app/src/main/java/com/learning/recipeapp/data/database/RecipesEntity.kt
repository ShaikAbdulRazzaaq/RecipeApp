package com.learning.recipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipeapp.models.RecipeResult
import com.learning.recipeapp.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(val recipeResult: RecipeResult) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}