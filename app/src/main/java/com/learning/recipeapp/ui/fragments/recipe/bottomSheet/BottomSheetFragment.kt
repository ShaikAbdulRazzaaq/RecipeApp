package com.learning.recipeapp.ui.fragments.recipe.bottomSheet

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.learning.recipeapp.R
import com.learning.recipeapp.databinding.FragmentRecipesBottomSheetBinding


class BottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_recipes_bottom_sheet) {

    private var _binding: FragmentRecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipesBottomSheetBinding.bind(view)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}