package com.example.ncnutrition.ui.mealTable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ncnutrition.databinding.FragmentProgressBinding
import java.util.*

class ProgressFragment : Fragment() {

    private var _binding :FragmentProgressBinding?=null
    private val binding get() = _binding!!

    private var selectedMealDate: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarViewProgress = binding.calendarViewProgress

        val minDate = Date()
        calendarViewProgress.minDate = minDate.time

        calendarViewProgress.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            selectedMealDate = calendar.time
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}