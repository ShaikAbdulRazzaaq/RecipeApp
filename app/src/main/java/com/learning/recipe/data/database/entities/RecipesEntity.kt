package com.learning.recipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(val recipeResult: RecipeResult) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}