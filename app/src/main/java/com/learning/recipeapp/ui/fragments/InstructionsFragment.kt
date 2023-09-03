package com.learning.recipeapp.ui.fragments

import androidx.fragment.app.Fragment
import com.learning.recipeapp.R


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {
    companion object {
        @JvmStatic
        fun getInstance() = InstructionsFragment()
    }
}