package com.learning.recipe.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.learning.recipe.R
import com.learning.recipe.adapters.RecipeDetailedPagerAdapter
import com.learning.recipe.data.database.entities.FavoritesEntity
import com.learning.recipe.databinding.ActivityDetailsBinding
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants.RECIPE_BUNDLE
import com.learning.recipe.utils.Constants.tabText
import com.learning.recipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel by viewModels<MainViewModel>()

    private var recipeSaved = false
    private var savedRecipeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailedToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Details"

        val recipeDetailedPagerAdapter = RecipeDetailedPagerAdapter(this, Bundle().apply {
            putParcelable(RECIPE_BUNDLE, args.result)
        })
        binding.detailedViewPager.adapter = recipeDetailedPagerAdapter
        TabLayoutMediator(
            binding.detailedTabLayout, binding.detailedViewPager
        ) { tab, position -> tab.text = tabText[position] }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if (item.itemId == R.id.save_to_favorites_menu) {
            if (recipeSaved) removeFromFavorites(item)
            else saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem?) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoriteEntityList ->
            kotlin.runCatching {
                favoriteEntityList.forEach {
                    if (it.result.id == args.result?.id) {
                        changeMenuItemColor(menuItem!!, R.color.sandy_brown)
                        savedRecipeId = it.result.id!!
                        recipeSaved = true
                    } else changeMenuItemColor(menuItem!!, R.color.black)
                }
            }.onFailure {
                Log.e(TAG, "checkSavedRecipes: ", it)
            }
        }
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun saveToFavorites(item: MenuItem) {
        if (args.result != null) {
            mainViewModel.insertFavoriteRecipe(
                FavoritesEntity(
                    0, args.result as RecipeResult.Result
                )
            )
            changeMenuItemColor(item, R.color.sandy_brown)
            showSnackBar("Recipe Saved")
            recipeSaved = true
        }
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedRecipeId, args.result as RecipeResult.Result)
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.black)
        showSnackBar("Removed From Favorites")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_SHORT).setAction("okay") {}
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "DetailsActivity"
    }
}