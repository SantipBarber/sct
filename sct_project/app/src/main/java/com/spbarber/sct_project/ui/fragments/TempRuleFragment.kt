package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentTempRuleBinding
import java.util.zip.Inflater

var durationProgram = "8"
var trainingDaysProgram = "7"
var frequencyMovement = "1"
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
            val durationProgramItem = binding.dropdownMenuDuration.selectedItem
            val trainingDaysItem = binding.dropdownMenuTrainingDays.selectedItem
            val frequencyID = binding.rgFrecuency.checkedRadioButtonId
            when(frequencyID){
                R.id.rb_frequency_1 -> frequencyMovement = "1"
                R.id.rb_frequency_2 -> frequencyMovement = "2"
                R.id.rb_frequency_3 -> frequencyMovement = "3"
            }
        }

        return binding.root

    }


    companion object{
        @JvmStatic
        fun newInstance(duration: Int, trainingDays: Int, frequency: Int){
            TempRuleFragment().apply {
                arguments = Bundle().apply {
                    putInt(durationProgram, duration)
                    putInt(trainingDaysProgram, trainingDays)
                    putInt(frequencyMovement, frequency)
                }
            }
        }
    }
}

