package com.learning.recipe.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.favoriteRecipeAdapter = favoritesRecipeAdapter
        binding.mainViewModel = viewModel

        binding.rvFavorites.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        binding.rvFavorites.adapter = favoritesRecipeAdapter
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "FavoriteRecipesFragment"
    }

}