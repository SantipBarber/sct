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
import com.spbarber.sct_project.adapters.ProgressRecyclerViewAdapter
import com.spbarber.sct_project.databinding.FragmentProgressBinding
import com.spbarber.sct_project.entities.VarsTraining
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import com.spbarber.sct_project.viewmodels.TrainingDataViewModel
import com.spbarber.sct_project.viewmodels.UsuarioViewModel

class ProgressFragment : Fragment() {
    private val TAG = "TAG"
    private lateinit var varsTraining: MutableMap<String, VarsTraining>
    private lateinit var binding: FragmentProgressBinding
    private val modelAthlete: AthleteViewModel by viewModels()
    private val modelTrainingData: TrainingDataViewModel by viewModels()
    private val modelUserViewModel: UsuarioViewModel by viewModels()
    private val madapter: ProgressRecyclerViewAdapter = ProgressRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rv = binding.rvProgress
        rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = madapter
        }
        //Crear el recycler view
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
                        val athlete = preferences?.name
                        val goal = preferences?.goal
                        val duration = "6 weeks"

                        Log.i(TAG, goal.toString())
                        Log.i(TAG, duration.toString())
                        modelTrainingData.getTrainingData(goal.toString(), duration.toString())
                            .observe(viewLifecycleOwner, { trainingData ->
                                /**
                                 * En el observador de TrainingDataViewModel getTrainingData()
                                 */
                                //madapter.newItems(trainingData)
                                //Actualizar los datos del adaptador
                                Log.i(TAG, trainingData.toString())

                            })
                    })
            })




        return binding.root
    }

}