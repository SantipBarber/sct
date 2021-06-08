package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentExperienceBinding
import com.spbarber.sct_project.entities.Preferences


class ExperienceFragment : Fragment() {
    private val TAG = "log"
    private lateinit var binding: FragmentExperienceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentExperienceBinding.inflate(layoutInflater)
        val view = binding.root
        var preferences = Preferences()
        binding.btnBackSigin.setOnClickListener {
            val action = ExperienceFragmentDirections.actionExperienceFragmentToInitFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnExperienceNext.setOnClickListener {
            var experienceUser = ""
            when(binding.rgExperience.checkedRadioButtonId){
                R.id.rb_experience_beginner -> experienceUser = "beginner"
                R.id.rb_experience_intermediate -> experienceUser = "intermediate"
                R.id.rb_experience_advance -> experienceUser = "advanced"
            }

            Log.i("TAG", App.getAuth().currentUser.toString())
            preferences.experience = experienceUser
            val action = ExperienceFragmentDirections.actionExperienceFragmentToGoalFragment(preferences)
            NavHostFragment.findNavController(this).navigate(action)
            Log.i(TAG, experienceUser)
        }
        return view
    }



}