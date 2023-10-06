package com.learning.recipe.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.recipe.models.RandomFoodJokeResponse
import com.learning.recipe.utils.Constants.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity (@Embedded var foodJokeResponse: RandomFoodJokeResponse){
    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}