package com.learning.recipeapp.ui.fragments.recipe.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.learning.recipeapp.R
import com.learning.recipeapp.databinding.FragmentRecipesBottomSheetBinding
import com.learning.recipeapp.utils.Constants.DEFAULT_DIET_TYPE
import com.learning.recipeapp.utils.Constants.DEFAULT_MEAL_TYPE
import com.learning.recipeapp.viewModels.RecipesViewModel
import java.util.Locale


class BottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_recipes_bottom_sheet) {

    private var _binding: FragmentRecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<RecipesViewModel>()

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeId = 0
    private var dietTypeId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipesBottomSheetBinding.bind(view)

        viewModel.readDietAndMealType.asLiveData(lifecycleScope.coroutineContext)
            .observe(viewLifecycleOwner) {
                mealTypeChip = it.selectedMealType
                dietTypeChip = it.selectedDietType
                updateChip(it.selectedMealTypeId, binding.mealTypeChipGroup)
                updateChip(it.selectedDietTypeId, binding.dietTypeChipGroup)
            }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val chip = group.findViewById<Chip>(group.checkedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())
            mealTypeChip = selectedMealType
            mealTypeId = group.checkedChipId
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val chip = group.findViewById<Chip>(group.checkedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.getDefault())
            dietTypeChip = selectedDietType
            dietTypeId = group.checkedChipId
        }

        binding.applyButton.setOnClickListener {
            viewModel.saveMealAndDietType(
                mealType = mealTypeChip,
                mealTypeId = mealTypeId,
                dietType = dietTypeChip,
                dietTypeId = dietTypeId
            )
            findNavController().navigate(
                BottomSheetFragmentDirections.actionBottomSheetFragmentToRecipeFragment(
                    backFromBottomSheet = true
                )
            )
        }
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            kotlin.runCatching { chipGroup.findViewById<Chip>(chipId).isChecked = true }.onFailure {
                Log.e(
                    TAG, "updateChip: Error : ${it.message} ", it
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "BottomSheetFragment"
    }
}