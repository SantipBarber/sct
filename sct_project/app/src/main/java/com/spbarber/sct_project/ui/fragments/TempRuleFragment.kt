package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentTempRuleBinding

class TempRuleFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var binding: FragmentTempRuleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTempRuleBinding.inflate(layoutInflater)

        val goal = arguments?.let {
            TempRuleFragmentArgs.fromBundle(it).goal
        }
        val experience = arguments?.let {
            GoalFragmentArgs.fromBundle(it).experience
        }

        Log.i(TAG, "$experience $goal")

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
            val action = TempRuleFragmentDirections.actionTempRuleFragmentToGoalFragment(experience)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnTempRuleNext.setOnClickListener {
            var durationProgramItem = binding.dropdownMenuDuration.selectedItem.toString()
            var trainingDaysItem = binding.dropdownMenuTrainingDays.selectedItem.toString()
            var frequencyMovement:Int = 1
            when(binding.rgFrecuency.checkedRadioButtonId){
                R.id.rb_frequency_1 -> frequencyMovement = 1
                R.id.rb_frequency_2 -> frequencyMovement = 2
                R.id.rb_frequency_3 -> frequencyMovement = 3
            }
            //Log.i("TAG", "$experience $goal $durationProgramItem $trainingDaysItem $frequencyMovement")
            val action = TempRuleFragmentDirections.actionTempRuleFragmentToRecordsFragment2(experience!!, goal!!, durationProgramItem, trainingDaysItem, frequencyMovement)
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root

    }

}

