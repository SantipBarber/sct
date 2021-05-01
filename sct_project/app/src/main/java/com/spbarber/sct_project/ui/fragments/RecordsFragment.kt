package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentRecordsBinding


class RecordsFragment : Fragment() {
    private lateinit var binding: FragmentRecordsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentRecordsBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_recordsFragment_to_tempRuleFragment)
        }

        binding.btnRecordsNext.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_recordsFragment_to_personalDataFragment)
        }


        return binding.root
    }


}