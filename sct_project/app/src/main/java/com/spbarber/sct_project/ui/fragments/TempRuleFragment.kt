package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentTempRuleBinding
import java.util.zip.Inflater

class TempRuleFragment : Fragment() {
    private lateinit var binding: FragmentTempRuleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTempRuleBinding.inflate(layoutInflater)

        val spinnerDuration: Spinner = binding.dropdownMenuDuration
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.duration_array,
                R.layout.custom_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerDuration.adapter = adapter
        }

        val spinnerFrequency: Spinner = binding.dropdownMenuTrainingDays
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.frequency_array,
                R.layout.custom_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrequency.adapter = adapter
        }

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_tempRuleFragment_to_goalFragment)
        }

        binding.btnTempRuleNext.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_tempRuleFragment_to_recordsFragment)
        }

        return binding.root

    }



}

