package com.example.ncnutrition.ui.foods.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ncnutrition.data.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentAddFoodBinding
import com.example.ncnutrition.model.Food
import com.example.ncnutrition.ui.foods.viewModel.FoodsViewModel
import com.example.ncnutrition.ui.foods.viewModel.FoodsViewModelFactory

class AddFoodFragment : Fragment() {

    private var _binding: FragmentAddFoodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FoodsViewModel by activityViewModels {
        FoodsViewModelFactory(
            (activity?.application as NCNutritionApplication).database.foodDao()
        )
    }
    lateinit var food:Food


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun isEntryValid():Boolean{
//        return viewModel.isEntryValid(
////            provide binding from inputs
//        )
        return false
    }

//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)
//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}