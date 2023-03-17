package com.example.ncnutrition.ui.deficiencies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ncnutrition.NCNutritionApplication
import com.example.ncnutrition.databinding.FragmentDeficiencyBinding
import com.example.ncnutrition.model.Deficiency
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModel
import com.example.ncnutrition.ui.deficiencies.viewModel.DeficiencyViewModelFactory


class DeficiencyFragment : Fragment() {

    private val viewModel : DeficiencyViewModel by activityViewModels {
        DeficiencyViewModelFactory(
            (activity?.application as NCNutritionApplication).database.deficiencyDao()
        )
    }

   private var _binding:FragmentDeficiencyBinding?=null
   private val binding get() = _binding!!

    private lateinit var deficiency: Deficiency

    private fun bind(deficiency: Deficiency){
        binding.apply {
            deficiencyDescription.text = deficiency.sign_and_symptoms
        }
    }
    private val navigationArgs:DeficiencyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentDeficiencyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveDeficiency(id).observe(this.viewLifecycleOwner){selectedDeficiency->
            deficiency = selectedDeficiency
            bind(deficiency)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}