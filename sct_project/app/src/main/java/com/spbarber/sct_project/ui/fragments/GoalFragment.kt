package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentGoalBinding
import com.spbarber.sct_project.viewmodels.TrainingDataViewModel

class GoalFragment : Fragment() {
    private lateinit var binding: FragmentGoalBinding
    private val model: TrainingDataViewModel by viewModels()
    private val TAG = "TAG"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater)
        val preferences = arguments?.let {
            GoalFragmentArgs.fromBundle(it).preferences
        }
        //Log.i(TAG, "$experience")


        binding.btnGoalBack.setOnClickListener {
            val action = GoalFragmentDirections.actionGoalFragmentToExperienceFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnGoalNext.setOnClickListener {
            var goalUser = ""
            val goalUserID = binding.rgTarget.checkedRadioButtonId
            when(goalUserID){
                R.id.rb_target_max_strength -> goalUser = "Maximal Strength"
                R.id.rb_target_strength -> goalUser = "Improve Endurance"
                R.id.rb_target_size -> goalUser = "Size"
            }
            preferences?.goal = goalUser

            val duration = "10 weeks"
            model.getTrainingData(goalUser.capitalize(), duration)
                .observe(viewLifecycleOwner, { trainingData ->
                if (!trainingData.trainingData.isNullOrEmpty()){
                    Log.i(TAG, "Lanzado el getTrainingData")
                    //
                }
            })

            val action = GoalFragmentDirections.actionGoalFragmentToTempRuleFragment(preferences)
            NavHostFragment.findNavController(this).navigate(action)
            Log.i(TAG, "$preferences")
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}