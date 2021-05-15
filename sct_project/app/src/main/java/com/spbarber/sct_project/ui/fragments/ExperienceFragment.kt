package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentExperienceBinding


class ExperienceFragment : Fragment() {
    private val TAG = "log"
    private lateinit var binding: FragmentExperienceBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentExperienceBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnBackSigin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_experienceFragment_to_initFragment)

        }

        binding.btnExperienceNext.setOnClickListener {
            var experienceUser = ""
            when(binding.rgExperience.checkedRadioButtonId){
                R.id.rb_experience_beginner -> experienceUser = "beginner"
                R.id.rb_experience_intermediate -> experienceUser = "intermediate"
                R.id.rb_experience_advance -> experienceUser = "advanced"
            }

            val action = ExperienceFragmentDirections.actionExperienceFragmentToGoalFragment(experienceUser)
            NavHostFragment.findNavController(this).navigate(action)
        }
        return view
    }



}