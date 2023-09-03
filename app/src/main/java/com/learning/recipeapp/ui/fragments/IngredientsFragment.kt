package com.learning.recipeapp.ui.fragments

import androidx.fragment.app.Fragment
import com.learning.recipeapp.R

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    companion object {
        @JvmStatic
        fun getInstance() = IngredientsFragment()
    }
}