package com.learning.recipeapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.learning.recipeapp.models.RecipeOnSearchResult

class RecipesDiffUtil(
    private val newList: List<RecipeOnSearchResult.Result>,
    private val oldList: List<RecipeOnSearchResult.Result>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}