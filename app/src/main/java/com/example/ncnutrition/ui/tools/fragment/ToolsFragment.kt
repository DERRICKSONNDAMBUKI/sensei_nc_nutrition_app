package com.example.ncnutrition.ui.tools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ncnutrition.databinding.FragmentToolsBinding

class ToolsFragment : Fragment() {

    private var _binding : FragmentToolsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ToolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentToolsBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}