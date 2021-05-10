package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentRecordsBinding

class RecordsFragment : Fragment() {
    private lateinit var binding: FragmentRecordsBinding
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

        binding.btnRecordsNext.setOnClickListener {
            val rmSquatuser = binding.tilRecordSquat.editText?.text.toString().toFloat()
            val rmPressUser = binding.tilRecordBenchPress.editText?.text.toString().toFloat()
            val rmDeadliftUser = binding.tilRecordDeadlift.editText?.text.toString().toFloat()

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