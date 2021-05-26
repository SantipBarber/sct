package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.adapters.ListViewAdapter
import com.spbarber.sct_project.adapters.SummaryRecyclerViewAdapter
import com.spbarber.sct_project.databinding.FragmentReviewAndConfirmBinding
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.TrainingDay
import com.spbarber.sct_project.viewmodels.UsuarioViewModel

class ReviewAndConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReviewAndConfirmBinding
    private val model: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewAndConfirmBinding.inflate(layoutInflater)

        val preferences = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).preferences
        }

        loadPersonalData(
            preferences?.name.toString(),
            preferences?.birthdate.toString(),
            preferences?.genre.toString(),
            preferences?.heigth.toString(),
            preferences?.weight.toString(),
            preferences?.experience.toString(),
            preferences?.goal.toString(),
            preferences?.days.toString(),
            preferences?.frequency.toString()
        )

        binding.btnBack.setOnClickListener {
            val action =
                ReviewAndConfirmFragmentDirections.actionReviewAndConfirmFragmentToSiginFragment(
                    preferences
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        loadProgramSummary(preferences!!)

        //Generar programa
        val user = getAuth().currentUser?.uid

        binding.btnGenerate.setOnClickListener {
            model.getUser(user.toString()).observe(viewLifecycleOwner, {
                //print(it.toString())
                //Log.i("TAG", "Mensaje $it")
                it.let {
                    binding.tvReviewName.setText(it?.lastName)
                }
            })
//            model.getPreferences(user.toString()).observe(viewLifecycleOwner, { prefs ->
//                print(prefs.toString())
//            })
        }


        return binding.root
    }

    private fun loadProgramSummary(preferences: Preferences) {
        //Contexto --> RequireContext() por estar en un fragment
        //Layout donde cargar los datos
        //Datos
        /*val adapter = ListViewAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            loadTrainingDays(preferences)
        )
        binding.programSummary.adapter = adapter*/

        val myAdapter = SummaryRecyclerViewAdapter(loadTrainingDays(preferences))
        val recyclerView = binding.programSummary

        recyclerView.apply {
            //Aquí configuramos de qué forma se visualizan las vistas
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = myAdapter
        }

    }



    private fun loadTrainingDays(preferences: Preferences): List<TrainingDay> {
        val trainingDays = mutableListOf<TrainingDay>()
        val days = preferences.days.toString()
        Log.i("TAG", days)
        when(days){
            "3 días" -> {
                trainingDays.add(TrainingDay(1, "Back Squat"))
                trainingDays.add(TrainingDay(2, "Bench Press"))
                trainingDays.add(TrainingDay(3, "Deadlift"))

            }
            "4 días" -> {

            }
            "5 días" -> {

            }
            "6 días" -> {
                trainingDays.add(TrainingDay(1, "Back Squat"))
                trainingDays.add(TrainingDay(2, "Bench Press"))
                trainingDays.add(TrainingDay(3, "Deadlift"))
                trainingDays.add(TrainingDay(4, "Squat Variant"))
                trainingDays.add(TrainingDay(5, "Bench Press Variant"))
                trainingDays.add(TrainingDay(6, "Deadlift Variant"))
            }
            else -> return trainingDays
        }
        Log.i("TAG", "${trainingDays.size}")

        return trainingDays
    }

    private fun loadPersonalData(
        name: String,
        birthdate: String,
        genre: String,
        heigth: String,
        weight: String,
        experience: String,
        goal: String,
        days: String,
        frequency: String
    ) {
        binding.tvReviewName.text = "Atleta: $name"
        //Hay que calcular la edad
        binding.tvReviewAge.text = "Edad: $birthdate"
        binding.tvReviewGenre.text = "Género: $genre"
        binding.tvReviewHeigth.text = "Altura: $heigth"
        binding.tvReviewWeight.text = "Peso: $weight"
        binding.tvReviewExperience.text = experience
        binding.tvReviewGoal.text = goal
        binding.tvReviewDays.text = days
        binding.tvReviewFrequency.text = "$frequency x movimiento"

    }


}

