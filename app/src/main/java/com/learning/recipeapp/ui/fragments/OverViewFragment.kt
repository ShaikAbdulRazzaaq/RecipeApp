package com.learning.recipeapp.ui.fragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.learning.recipeapp.R
import com.learning.recipeapp.databinding.FragmentOverViewBinding
import com.learning.recipeapp.models.RecipeResult
import com.learning.recipeapp.utils.Constants.RECIPE_BUNDLE

class OverViewFragment : Fragment(R.layout.fragment_over_view) {

    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOverViewBinding.bind(view)

        val result =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arguments?.getParcelable(
                RECIPE_BUNDLE, RecipeResult.Result::class.java
            ) else arguments?.getParcelable(RECIPE_BUNDLE) as? RecipeResult.Result
        binding.apply {
            result?.apply {
                foodImage.load(image)
                tvLikes.text = aggregateLikes?.toString()
                tvTime.text = readyInMinutes?.toString()
                tvSummary.text = summary?.let {
                    buildSpannedString {
                        append(HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT))
                    }/* Jsoup.parse(it).text()*/
                }
                tvTitle.text = title
                vegetarian?.let { if (it) tvVegetarian.setTextAndIconColorToGreen() }
                vegan?.let { if (it) tvVegan.setTextAndIconColorToGreen() }
                glutenFree?.let { if (it) tvGlutenFree.setTextAndIconColorToGreen() }
                dairyFree?.let { if (it) tvDairyFree.setTextAndIconColorToGreen() }
                veryHealthy?.let { if (it) tvHealthy.setTextAndIconColorToGreen() }
                cheap?.let { if (it) tvCheap.setTextAndIconColorToGreen() }
            }
        }
    }

    private fun MaterialTextView.setTextAndIconColorToGreen() {
        setTextColor(
            ContextCompat.getColor(
                requireActivity(), R.color.jungle_green
            )
        )
        TextViewCompat.setCompoundDrawableTintList(
            this, ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireActivity(), R.color.jungle_green
                )
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() = OverViewFragment()
    }
}