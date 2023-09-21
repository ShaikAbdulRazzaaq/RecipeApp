package com.learning.recipe.models


import com.google.gson.annotations.SerializedName

data class RandomFoodJokeResponse(
    @SerializedName("text") val text: String? // Any salad can be a Caesar salad if you stab it enough.
)