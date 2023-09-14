package com.learning.recipe.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.learning.recipe.R
import com.learning.recipe.databinding.FragmentInstructionsBinding
import com.learning.recipe.models.RecipeResult
import com.learning.recipe.utils.Constants


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInstructionsBinding.bind(view)

        val result =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                Constants.RECIPE_BUNDLE, RecipeResult.Result::class.java
            ) else arguments?.getParcelable(Constants.RECIPE_BUNDLE) as? RecipeResult.Result

        result?.sourceUrl?.let {
            binding.instructionsWebView.webViewClient = WebViewClient()
            binding.instructionsWebView.loadUrl(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun getInstance() = InstructionsFragment()
    }
}