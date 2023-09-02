package com.learning.recipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipeapp.models.RecipeOnSearchResult
import com.learning.recipeapp.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(val recipeOnSearchResult: RecipeOnSearchResult) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}