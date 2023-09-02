package com.learning.recipeapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.learning.recipeapp.models.RecipeResult

class RecipesDiffUtil(
    private val newList: List<RecipeResult.Result>,
    private val oldList: List<RecipeResult.Result>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}