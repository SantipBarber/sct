package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentGoalBinding

class GoalFragment : Fragment() {
    private lateinit var binding: FragmentGoalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater)
        val experience = arguments?.let {
            GoalFragmentArgs.fromBundle(it).experience
        }

        binding.btnGoalBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_goalFragment_to_experienceFragment)
        }

        binding.btnGoalNext.setOnClickListener {
            var goalUser = ""
            val goalUserID = binding.rgTarget.checkedRadioButtonId
            when(goalUserID){
                R.id.rb_target_max_strength -> goalUser = "maxStrength"
                R.id.rb_target_strength -> goalUser = "improveEndurance"
                R.id.rb_target_size -> goalUser = "size"
            }

            val action = GoalFragmentDirections.actionGoalFragmentToTempRuleFragment(experience, goalUser)
            NavHostFragment.findNavController(this).navigate(action)
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}