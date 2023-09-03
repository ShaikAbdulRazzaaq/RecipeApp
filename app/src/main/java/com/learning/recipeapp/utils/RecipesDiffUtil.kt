package com.learning.recipeapp.utils

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil<T>(
    private val newList: List<T>,
    private val oldList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}