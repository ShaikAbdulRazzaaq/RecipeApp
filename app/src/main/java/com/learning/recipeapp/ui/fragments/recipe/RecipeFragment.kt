package com.learning.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipeapp.MainViewModel
import com.learning.recipeapp.R
import com.learning.recipeapp.adapters.RecipesAdapter
import com.learning.recipeapp.data.NetworkResult
import com.learning.recipeapp.databinding.FragmentRecipeBinding
import com.learning.recipeapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipesAdapter by lazy {
        RecipesAdapter()
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeBinding.bind(view)
        setUpRecyclerView()
    }

    private fun requestApiData() {
        viewModel.getRecipes(applyQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> TODO()
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> hideShimmerEffect()
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries = hashMapOf<String, String>()
        queries["number"] = "50"
        queries["apiKey"] = Constants.API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun setUpRecyclerView() {
        binding.shimmerRecyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            showShimmerEffect()
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerRecyclerView.showShimmer()
    }

    fun hideShimmerEffect() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}