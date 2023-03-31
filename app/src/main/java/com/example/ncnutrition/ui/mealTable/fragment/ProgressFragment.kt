package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentProgressBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.mealTable.viewModel.MealTotals
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModel
import com.example.ncnutrition.ui.mealTable.viewModel.MealViewModelFactory
import java.time.ZoneId
import java.util.*

class ProgressFragment : Fragment() {

    private val viewModel: MealViewModel by activityViewModels {
        MealViewModelFactory(
            (activity?.application as NCNutritionApplication).database.mealDao()
        )
    }

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private var selectedMealDate: Date = Date()
    lateinit var mealFoods: List<Food>
    lateinit var mealTotals: MealTotals

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarViewProgress = binding.calendarViewProgress

//        val minDate = Date()
//        calendarViewProgress.minDate = minDate.time

        calendarViewProgress.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            selectedMealDate = calendar.time
            selectDate(selectedMealDate)
        }

    }

    private fun selectDate(selectedMealDate: Date) {
        viewModel.getMealsBefore(selectedMealDate).observe(this.viewLifecycleOwner) {
            mealFoods = it
            if (mealFoods.isEmpty()) {
                Toast.makeText(
                    context,
                    "${mealFoods.count()} no meals on $selectedMealDate",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                mealTotals = MealTotals(mealFoods)
                bindTotals(mealTotals)

//                binding.textViewProgress.text = mealFoods.toString()
                Toast.makeText(
                    context,
                    "${mealFoods.count()} meals by date ${
                        selectedMealDate.toInstant().atZone(ZoneId.systemDefault()).dayOfMonth
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun bindTotals(mealTotals: MealTotals) {
        binding.apply {
            //        energy
            textViewEnergyInKJ.text = "${mealTotals.energyInKcal} Kilojoules"
            textViewEnergyInKcal.text = "${mealTotals.energyInKcal} Kilocalories"

            //        proximate
            textViewWaterInG.text = "${mealTotals.waterInG} g"
            textViewProteinInG.text = "${mealTotals.proteinInG} g"
            textViewFatInG.text = "${mealTotals.fatInG} g"
            textViewCarbohydrateAvailableInG.text = "${mealTotals.carbohydrateAvailableInG} g"
            textViewFibreInG.text="${mealTotals.fibreInG} g"
            textViewAshInG.text ="${mealTotals.ashInG} g"

   //        minerals
            textViewCaInMg.text = "${mealTotals.caInMg} mg"
            textViewFeInMg.text = "${mealTotals.feInMg} mg"
            textViewMgInMg.text = "${mealTotals.mgInMg} mg"
            textViewPInMg.text = "${mealTotals.pInMg} mg"
            textViewKInMg.text = "${mealTotals.kInMg} mg"
            textViewNaInMg.text = "${mealTotals.naInMg} mg"
            textViewZnInMg.text = "${mealTotals.znInMg} mg"
            textViewSeInMg.text = "${mealTotals.seInMg} mg"

    //        vitamins
            textViewVitARaeInMcg.text  ="${mealTotals.vitARaeInMcg} mcg"
            textViewVitAReInMcg.text ="${mealTotals.vitAReInMcg} mcg"
            textViewRetinolInMcg.text = "${mealTotals.retinolInMcg} mcg"
            textViewBetaCaroteneEquivalentInMcg.text = "${mealTotals.betaCaroteneEquivalentInMcg} mcg"
            textViewThiaminInMcg.text ="${mealTotals.thiaminInMcg} mcg"
            textViewRiboflavinInMcg.text ="${mealTotals.riboflavinInMcg} mcg"
            textViewNiacinInMcg.text = "${mealTotals.niacinInMcg}  mcg"
            textViewDietaryFolateInMcg.text = "${mealTotals.dietaryFolateEqInMcg} mcg"
            textViewFoodFolateInMcg.text ="${mealTotals.foodFolateInMcg} mcg"
            textViewVitB12InMcg.text ="${mealTotals.vitB12InMcg} mcg"
            textViewVitCInMcg.text = "${mealTotals.vitCInMcg} mcg"

    //        cholesterol
            textViewCholesterolInMg.text = "${mealTotals.cholesterolInMg} mg"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}