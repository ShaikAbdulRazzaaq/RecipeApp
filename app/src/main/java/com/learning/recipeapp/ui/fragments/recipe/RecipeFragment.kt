package com.learning.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.learning.recipeapp.R
import com.learning.recipeapp.databinding.FragmentRecipeBinding


class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeBinding.bind(view)
        binding.shimmerRecyclerView.showShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}