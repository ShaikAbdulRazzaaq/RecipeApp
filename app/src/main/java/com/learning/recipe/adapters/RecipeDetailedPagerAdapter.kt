package com.learning.recipe.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.learning.recipe.utils.Constants.fragments

class RecipeDetailedPagerAdapter(fragmentActivity: FragmentActivity, val result: Bundle) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].apply {
        arguments = result
    }
}