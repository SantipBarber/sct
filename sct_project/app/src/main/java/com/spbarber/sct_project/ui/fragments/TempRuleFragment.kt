package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentTempRuleBinding

class TempRuleFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var binding: FragmentTempRuleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTempRuleBinding.inflate(layoutInflater)

        val preferences = arguments?.let {
            TempRuleFragmentArgs.fromBundle(it).preferences
        }


        val spinnerDuration: Spinner = binding.dropdownMenuDuration
        // Creamos un ArrayAdapter usando el string-array deseado y el diseño de Spinner deseado
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.duration_array,
            R.layout.custom_spinner_item
        ).also { adapter ->
            // Especifique el diseño que se utilizará cuando aparezca la lista de opciones
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Aplicamos el adaptador al spinner o ruleta
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
            val action =
                TempRuleFragmentDirections.actionTempRuleFragmentToGoalFragment(preferences)
            NavHostFragment.findNavController(this).navigate(action)
        }

        spinnerFrequency.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id.toInt() == 0) {
                    binding.rgFrecuency.check(R.id.rb_frequency_1)
                } else if (id.toInt() == 1){
                    binding.rgFrecuency.check(R.id.rb_frequency_2)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.btnTempRuleNext.setOnClickListener {
            val durationProgramItem = binding.dropdownMenuDuration.selectedItem.toString()
            val trainingDaysItem = binding.dropdownMenuTrainingDays.selectedItem.toString()

            var frequencyMovement: Int = 1

            when (binding.rgFrecuency.checkedRadioButtonId) {
                R.id.rb_frequency_1 -> frequencyMovement = 1
                R.id.rb_frequency_2 -> frequencyMovement = 2
                //R.id.rb_frequency_3 -> frequencyMovement = 3
            }
            preferences?.duration = durationProgramItem
            preferences?.days = trainingDaysItem
            preferences?.frequency = frequencyMovement
            Log.i("TAG", frequencyMovement.toString())
            val action =
                TempRuleFragmentDirections.actionTempRuleFragmentToRecordsFragment2(preferences)
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root

    }

    fun changeFrequency(){

    }

}

