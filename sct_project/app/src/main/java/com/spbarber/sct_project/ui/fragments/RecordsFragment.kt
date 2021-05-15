package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment() {
    private lateinit var binding: FragmentRecordsBinding
    private var rmSquatuser: Float = 0.0F
    private var rmPressUser: Float = 0.0F
    private var rmDeadliftUser: Float = 0.0F
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentRecordsBinding.inflate(layoutInflater)

        val experience = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).experience
        }
        val goal = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).goal
        }
        val duration = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).duration
        }
        val days = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).ndays
        }
        val frequency = arguments?.let {
            RecordsFragmentArgs.fromBundle(it).frequency
        }

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_recordsFragment2_to_tempRuleFragment)
        }

        binding.tietRecordSquat.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordSquat.error = ""
                    //binding.tilRecordSquat.editText?.text?.clear()
                }

            }
        }
        binding.tietRecordSquat.addTextChangedListener {
            val minSquatRecord = it!!.length
            if (minSquatRecord < 2) {
                binding.tilRecordSquat.error = "Debes introducir tu marca personal"
            } else {
                binding.tilRecordSquat.error = ""
                binding.tilRecordSquat.helperText = ""
            }
        }
        binding.tietRecordBenchPress.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordBenchPress.error = ""
                    //binding.tilRecordBenchPress.editText?.text?.clear()
                }
            }
        }
        binding.tietRecordBenchPress.addTextChangedListener {
            val minPressRecord = it!!.length
            if (minPressRecord < 2) {
                binding.tilRecordBenchPress.error = "Debes introducir tu marca personal"
            } else {
                binding.tilRecordBenchPress.error = ""
                binding.tilRecordBenchPress.helperText = ""
            }
        }
        binding.tietRecordDeadlift.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilRecordDeadlift.error = ""
                    //binding.tilRecordDeadlift.editText?.text?.clear()
                }
            }
        }
        binding.tietRecordDeadlift.addTextChangedListener {
            val minDeadliftRecord = it!!.length
            if (minDeadliftRecord < 2) {
                binding.tilRecordDeadlift.error = "Debes introducir tu marca personal"
            } else {
                binding.tilRecordDeadlift.error = ""
                binding.tilRecordDeadlift.helperText = ""
            }
        }

        binding.btnRecordsNext.setOnClickListener {
            rmSquatuser = binding.tilRecordSquat.editText!!.text.toString().toFloat()
            if (rmSquatuser < 50 || binding.tilRecordSquat.isEmpty()) {
                binding.tilRecordSquat.error = "Debes introducir una marca valida para la sentadilla"
                return@setOnClickListener
            }
            rmPressUser = binding.tilRecordBenchPress.editText!!.text.toString().toFloat()
            if (rmPressUser < 50 || binding.tilRecordBenchPress.isEmpty()) {
                binding.tilRecordBenchPress.error = "Debes introducir una marca válida para la banca"
                return@setOnClickListener
            }
            rmDeadliftUser = binding.tilRecordDeadlift.editText!!.text.toString().toFloat()
            if (rmDeadliftUser < 50 || binding.tilRecordDeadlift.isEmpty()) {
                binding.tilRecordDeadlift.error = "Debes introducir una marca válida para el peso muerto"
                return@setOnClickListener
            }


            val action = RecordsFragmentDirections
                    .actionRecordsFragment2ToPersonalDataFragment(
                            experience,
                            goal,
                            duration,
                            days,
                            frequency!!,
                            rmSquatuser,
                            rmPressUser,
                            rmDeadliftUser
                    )
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

}