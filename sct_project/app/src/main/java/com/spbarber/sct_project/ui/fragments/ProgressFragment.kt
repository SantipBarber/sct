package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbarber.sct_project.App
import com.spbarber.sct_project.databinding.FragmentProgressBinding
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.Week
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import com.spbarber.sct_project.viewmodels.UserViewModel
import com.ºspbarber.sct_project.adapters.ProgressRecyclerViewAdapter

class ProgressFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var binding: FragmentProgressBinding
    private val modelAthlete: AthleteViewModel by viewModels()
    private val modelUserViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(layoutInflater)

        modelUserViewModel.getUser(App.getAuth().uid.toString())
            .observe(viewLifecycleOwner, { userData ->
                /**
                 * En el observador de UsuarioViewModel getUser()
                 */
                modelUserViewModel.getPreferences(App.getAuth().uid.toString())
                    .observe(viewLifecycleOwner, { preferencesData ->
                        /**
                         * En el observador de UsuarioViewModel getPreferences()
                         */
                        val preferences = preferencesData
                        val athlete = preferences!!.name
                        modelAthlete.getAthlete(athlete!!).observe(viewLifecycleOwner, {athleteData ->
                            createRecyclerView(athleteData.programs[0].weeks)
                        })
                    })
            })

        return binding.root
    }

    private fun createRecyclerView(weeks: List<Week>){
        val myAdapter = ProgressRecyclerViewAdapter(weeks as MutableList<Week>)
        val recyclerView = binding.rvProgress
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = myAdapter
        }
    }

    private fun getWeeks(preferences: Preferences): Int {
        Log.i(TAG, "En la función ${preferences.duration.toString()}")
        var durationProgram = 6
        when (preferences.duration.toString()) {
            "6 weeks" -> durationProgram = 6
            "8 weeks" -> durationProgram = 8
            "10 weeks" -> durationProgram = 10
            "12 weeks" -> durationProgram = 12
            else -> durationProgram = 6
        }
        return durationProgram
    }
}