package com.learning.recipe.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.recipe.R
import com.learning.recipe.adapters.IngredientsAdapter
import com.learning.recipe.databinding.FragmentIngredientsBinding
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val recyclerAdapter by lazy {
        IngredientsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIngredientsBinding.bind(view)

        val result =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                Constants.RECIPE_BUNDLE, RecipeResult.Result::class.java
            ) else arguments?.getParcelable(Constants.RECIPE_BUNDLE) as? RecipeResult.Result

        setUpRecyclerView()

        result?.extendedIngredients?.let {
            recyclerAdapter.setData(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.ingredientsRecyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun getInstance() = IngredientsFragment()
    }
}