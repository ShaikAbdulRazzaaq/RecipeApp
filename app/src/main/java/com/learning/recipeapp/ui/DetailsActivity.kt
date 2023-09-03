package com.learning.recipeapp.ui

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
import com.learning.recipeapp.R
import com.learning.recipeapp.adapters.RecipeDetailedPagerAdapter
import com.learning.recipeapp.data.database.entities.FavoritesEntity
import com.learning.recipeapp.databinding.ActivityDetailsBinding
import com.learning.recipeapp.models.RecipeResult
import com.learning.recipeapp.utils.Constants.RECIPE_BUNDLE
import com.learning.recipeapp.utils.Constants.tabText
import com.learning.recipeapp.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel by viewModels<MainViewModel>()
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

    private fun checkSavedRecipes(menuItem: MenuItem?) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoriteEntityList ->
            kotlin.runCatching {
                favoriteEntityList.forEach {
                    if (it.result.id == args.result?.id) {
                        changeMenuItemColor(menuItem!!, R.color.sandy_brown)
                    }
                }
            }.onFailure {
                Log.e(TAG, "checkSavedRecipes: ", it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorites_menu) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
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
        }
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