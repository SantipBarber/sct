package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentExperienceBinding
import java.lang.Math.log

var experienceUser = ""
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
            NavHostFragment.findNavController(this).navigate(R.id.action_experienceFragment_to_goalFragment)
            val experienceUserID = binding.rgExperience.checkedRadioButtonId
            when(experienceUserID){
                R.id.rb_experience_beginner -> experienceUser = "beginner"
                R.id.rb_experience_intermediate -> experienceUser = "intermediate"
                R.id.rb_experience_advance -> experienceUser = "advanced"
            }
        }
        return view
    }

    companion object{
        @JvmStatic
        fun newInstance(experience:String){
            ExperienceFragment().apply {
                arguments = Bundle().apply {
                    //Mapa: String, valor
                    putString(experienceUser, experience)
                }
            }
        }
    }

}