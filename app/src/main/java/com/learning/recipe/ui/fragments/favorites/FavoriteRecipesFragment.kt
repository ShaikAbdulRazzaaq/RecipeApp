package com.learning.recipe.ui.fragments.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipe.R
import com.learning.recipe.adapters.FavoritesRecipeAdapter
import com.learning.recipe.databinding.FragmentFavoriteRecipesBinding
import com.learning.recipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(R.layout.fragment_favorite_recipes) {
    private val favoritesRecipeAdapter by lazy { FavoritesRecipeAdapter() }
    private val viewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteRecipesBinding.bind(view)

        binding.rvFavorites.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rvFavorites.adapter = favoritesRecipeAdapter

        viewModel.readFavoriteRecipes.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated:$it ")
            favoritesRecipeAdapter.setData(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "FavoriteRecipesFragment"
    }

}