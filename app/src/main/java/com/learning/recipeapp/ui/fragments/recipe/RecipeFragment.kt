package com.learning.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipeapp.R
import com.learning.recipeapp.adapters.RecipesAdapter
import com.learning.recipeapp.data.network.NetworkResult
import com.learning.recipeapp.databinding.FragmentRecipeBinding
import com.learning.recipeapp.viewModels.MainViewModel
import com.learning.recipeapp.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipesAdapter by lazy {
        RecipesAdapter()
    }

    private val viewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<RecipesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeBinding.bind(view)
        setUpRecyclerView()
        requestApiData()
    }

    private fun requestApiData() {
        viewModel.getRecipes(recipesViewModel.applyQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireActivity(), "${response.message}", Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }

                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        recipesAdapter.setData(it)
                    }
                }
            }
        }
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