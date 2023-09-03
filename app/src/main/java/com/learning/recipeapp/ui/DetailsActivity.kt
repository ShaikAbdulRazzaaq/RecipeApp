package com.learning.recipeapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.learning.recipeapp.adapters.RecipeDetailedPagerAdapter
import com.learning.recipeapp.databinding.ActivityDetailsBinding
import com.learning.recipeapp.utils.Constants.RECIPE_BUNDLE
import com.learning.recipeapp.utils.Constants.tabText

class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsActivityArgs>()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}