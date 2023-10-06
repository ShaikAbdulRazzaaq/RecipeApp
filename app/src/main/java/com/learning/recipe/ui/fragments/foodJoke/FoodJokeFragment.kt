package com.learning.recipe.ui.fragments.foodJoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.learning.recipe.R
import com.learning.recipe.data.network.NetworkResult
import com.learning.recipe.databinding.FragmentFoodJokeBinding
import com.learning.recipe.utils.Constants.API_KEY
import com.learning.recipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke!"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)

        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "${networkResult.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    loadDataFromCache()
                }

                is NetworkResult.Loading -> Log.d(TAG, "onCreateView: Loading Food Joke Response")
                is NetworkResult.Success -> {
                    binding.foodJokeText.text = networkResult.data?.text
                    networkResult.data?.let { foodJokeResponse ->
                        foodJokeResponse.text?.let {
                            binding.foodJokeText.text = it
                            foodJoke = it
                        }
                    }

                }
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.food_joke_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.share_food_joke_menu) {
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        type="text/plain"
                        putExtra(Intent.EXTRA_TEXT,foodJoke)
                        startActivity(this)
                    }
                }
                return true
            }

        })

        return _binding?.root
    }

    private fun loadDataFromCache() {
        mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { foodJokeEntities ->
            if (foodJokeEntities.isNullOrEmpty().not()) {
                foodJokeEntities[0].foodJokeResponse.text?.let {
                    binding.foodJokeText.text = it
                    foodJoke = it
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "FoodJokeFragment"
    }
}