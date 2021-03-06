package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var binding: FragmentRecordsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentRecordsBinding.inflate(layoutInflater)

        val preferences = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).preferences
        }

        binding.btnBack.setOnClickListener {
            val action = RecordsFragmentDirections.actionRecordsFragment2ToTempRuleFragment(preferences)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.tietRecordSquat.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordSquat.error = ""
                    binding.tietRecordSquat.text?.clear()
                }
            }
        }
        binding.tietRecordSquat.addTextChangedListener {
            val minSquatRecord = it!!.length
            if (minSquatRecord < 2) {
                binding.tilRecordSquat.error = "Debes introducir tu marca personal"
            } else if (minSquatRecord < 1){
                it.insert(0, "0")
            } else {
                binding.tilRecordSquat.error = ""
                binding.tilRecordSquat.helperText = ""
            }
        }
        binding.tietRecordBenchPress.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordBenchPress.error = ""
                    binding.tietRecordBenchPress.text?.clear()
                }
            }
        }
        binding.tietRecordBenchPress.addTextChangedListener {
            val minPressRecord = it!!.length
            if (minPressRecord < 2) {
                binding.tilRecordBenchPress.error = "Debes introducir tu marca personal"
            } else if (minPressRecord < 1){
                it.insert(0, "0")
            } else {
                binding.tilRecordBenchPress.error = ""
                binding.tilRecordBenchPress.helperText = ""
            }
        }
        binding.tietRecordDeadlift.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordDeadlift.error = ""
                    binding.tietRecordDeadlift.text?.clear()
                }
            }
        }
        binding.tietRecordDeadlift.addTextChangedListener {
            val minDeadliftRecord = it!!.length
            if (minDeadliftRecord < 2) {
                binding.tilRecordDeadlift.error = "Debes introducir tu marca personal"
            } else if (minDeadliftRecord < 1){
                it.insert(0, "0")
            } else {
                binding.tilRecordDeadlift.error = ""
                binding.tilRecordDeadlift.helperText = ""
            }
        }

        binding.btnRecordsNext.setOnClickListener {
            val rmSquatuser = binding.tietRecordSquat
            val rmPressUser = binding.tietRecordBenchPress
            val rmDeadliftUser = binding.tietRecordDeadlift
            val recordsUser = listOf(rmSquatuser, rmPressUser, rmDeadliftUser)
            var error = false
            recordsUser.forEach {
                if (it.getInputText().isBlank()){
                    error = true
                    when(it.id){
                        R.id.til_record_squat -> {
                            binding.tilRecordSquat.error = "Introduce una marca v??lida"
                        }
                        R.id.til_record_bench_press -> {
                            binding.tilRecordBenchPress.error = "Introduce una marca v??lida"
                        }
                        R.id.til_record_deadlift -> {
                            binding.tilRecordDeadlift.error = "Introduce una marca v??lida"
                        }
                    }
                }
            }
            if (error) return@setOnClickListener
            if (rmSquatuser.getInputText().toFloat() < 50) {
                binding.tilRecordSquat.error = "Debes introducir una marca v??lida para la sentadilla"
                return@setOnClickListener
            }
            if (rmPressUser.getInputText().toFloat() < 50) {
                binding.tilRecordBenchPress.error = "Debes introducir una marca v??lida para la banca"
                return@setOnClickListener
            }
            if (rmDeadliftUser.getInputText().toFloat() < 50) {
                binding.tilRecordDeadlift.error = "Debes introducir una marca v??lida para el peso muerto"
                return@setOnClickListener
            }
            preferences?.rmSquat = rmSquatuser.getInputText().toFloat()
            preferences?.rmPress = rmPressUser.getInputText().toFloat()
            preferences?.rmDeadlift = rmDeadliftUser.getInputText().toFloat()

            val action = RecordsFragmentDirections
                    .actionRecordsFragment2ToPersonalDataFragment(
                            preferences
                    )
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }
    fun TextInputEditText.getInputText(): String{
        return text.toString()
    }

}