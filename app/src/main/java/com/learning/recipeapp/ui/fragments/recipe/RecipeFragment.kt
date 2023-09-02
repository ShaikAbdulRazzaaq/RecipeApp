package com.learning.recipeapp.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipeapp.R
import com.learning.recipeapp.adapters.RecipesAdapter
import com.learning.recipeapp.data.network.NetworkResult
import com.learning.recipeapp.databinding.FragmentRecipeBinding
import com.learning.recipeapp.utils.NetworkListener
import com.learning.recipeapp.utils.observeOnce
import com.learning.recipeapp.viewModels.MainViewModel
import com.learning.recipeapp.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipesAdapter by lazy {
        RecipesAdapter()
    }

    private val args by navArgs<RecipeFragmentArgs>()

    private val viewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<RecipesViewModel>()
    private lateinit var networkListener: NetworkListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipeBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fabSearch.setOnClickListener {
            if (recipesViewModel.networkStatus) findNavController().navigate(R.id.action_recipeFragment_to_bottomSheetFragment)
            else recipesViewModel.showNetworkStatus()
        }

        setUpRecyclerView()
        readDatabase()

        lifecycleScope.launch(Dispatchers.IO) {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireActivity()).collect {
                Log.d(TAG, "onViewCreated: NetworkListener $it")
                recipesViewModel.networkStatus = it
                recipesViewModel.showNetworkStatus()
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

    private fun readDatabase() {
        lifecycleScope.launch {
            viewModel.readDatabase.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    recipesAdapter.setData(database[0].recipeResult)
                    hideShimmerEffect()
                } else requestApiData()
            }
        }
    }

    private fun requestApiData() {
        viewModel.getRecipes(recipesViewModel.applyQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireActivity(), "${response.message}", Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Loading -> showShimmerEffect()

                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        recipesAdapter.setData(it)
                    }
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.readDatabase.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.setData(database[0].recipeResult)
                }
            }
        }
    }


    private fun showShimmerEffect() {
        binding.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "RecipeFragment"
    }

}