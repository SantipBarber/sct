package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentTempRuleBinding

class TempRuleFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var binding: FragmentTempRuleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTempRuleBinding.inflate(layoutInflater)

        val preferences = arguments?.let {
            TempRuleFragmentArgs.fromBundle(it).preferences
        }


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
            val action = TempRuleFragmentDirections.actionTempRuleFragmentToGoalFragment(preferences)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnTempRuleNext.setOnClickListener {
            var durationProgramItem = binding.dropdownMenuDuration.selectedItem.toString()
            var trainingDaysItem = binding.dropdownMenuTrainingDays.selectedItem.toString()
            var frequencyMovement:Int = 1
            when(binding.rgFrecuency.checkedRadioButtonId){
                R.id.rb_frequency_1 -> frequencyMovement = 1
                R.id.rb_frequency_2 -> frequencyMovement = 2
                //R.id.rb_frequency_3 -> frequencyMovement = 3
            }
            preferences?.duration = durationProgramItem
            preferences?.days = trainingDaysItem
            preferences?.frequency = frequencyMovement
            val action = TempRuleFragmentDirections.actionTempRuleFragmentToRecordsFragment2(preferences)
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root

    }

}

