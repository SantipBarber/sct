package com.spbarber.sct_project.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestoreException
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.adapters.SummaryRecyclerViewAdapter
import com.spbarber.sct_project.databinding.FragmentReviewAndConfirmBinding
import com.spbarber.sct_project.entities.*
import com.spbarber.sct_project.ui.activities.AppActivity
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import java.util.*

class ReviewAndConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReviewAndConfirmBinding
    private val modelAthlete: AthleteViewModel by viewModels()

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



        binding.btnBack.setOnClickListener {
            val action =
                ReviewAndConfirmFragmentDirections.actionReviewAndConfirmFragmentToSiginFragment(
                    preferences
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        loadProgramSummary(preferences!!)

        binding.btnGenerate.setOnClickListener {
            val records = mutableListOf<Record>()
            val idExerciseSquat = "squat"
            val idExercisePress = "benchpress"
            val idExerciseDeadlift = "deadlift"
            val recordSquat = Record(
                Date(System.currentTimeMillis()),
                preferences.rmSquat,
                idExerciseSquat
            )
            val recordPress = Record(
                Date(System.currentTimeMillis()),
                preferences.rmPress,
                idExercisePress
            )
            val recordDeadlift = Record(
                Date(System.currentTimeMillis()),
                preferences.rmDeadlift,
                idExerciseDeadlift
            )
            val recordsAthlete = listOf(recordSquat, recordPress, recordDeadlift)
            records.addAll(recordsAthlete)

            val programs = mutableListOf<Program>()
            val program1 = Program(
                1,
                "Primer programa",
                Date(
                    System.currentTimeMillis()
                ),
                Date(System.currentTimeMillis()),
                preferences.goal.toString(),
                preferences.duration.toString()
            )
            programs.add(program1)


            val trainingDays = loadTrainingDays(preferences)
            val newAthlete = Athlete(
                preferences.name.toString(),
                preferences.heigth,
                preferences.weight,
                preferences.birthdate.toString(),
                preferences.genre.toString(),
                getAuth().currentUser?.uid.toString(),
                records,
                programs,
                trainingDays
            )
            modelAthlete.createAthlete(newAthlete).observe(viewLifecycleOwner, { exception ->
                when (exception) {
                    is FirebaseFirestoreException -> {
                        Log.i("TAG", "no se ha podido almacenar el atleta")
                    }
                }
            })
            goToApp()
        }

        return binding.root
    }

    private fun loadTrainingDays(preferences: Preferences): List<TrainingDay> {
        val trainingDays = mutableListOf<TrainingDay>()
        when(preferences.days.toString()){
            "3 días" -> {
                val day1 = TrainingDay(1, "Sentadilla", "Si")
                val day2 = TrainingDay(2, "Cardio", "Opcional")
                val day3 = TrainingDay(3, "Press banca", "Si")
                val day4 = TrainingDay(4, "Descanso", "No")
                val day5 = TrainingDay(5, "Peso muerto", "Si")
                val day6 = TrainingDay(6, "Cardio", "Opcional")
                val day7 = TrainingDay(7, "Descanso", "No")
                val days = listOf(day1, day2, day3, day4, day5, day6, day7)
                trainingDays.addAll(days)

            }
            "5 días" -> {

            }
            "6 días" -> {
                val day1 = TrainingDay(1, "Sentadilla", "No")
                val day2 = TrainingDay(2, "Peso muerto variante", "Si")
                val day3 = TrainingDay(3, "Press banca", "No")
                val day4 = TrainingDay(4, "Sentadilla variante", "Si")
                val day5 = TrainingDay(5, "Peso muerto", "No")
                val day6 = TrainingDay(6, "Press banca variante", "Si")
                val day7 = TrainingDay(7, "Descanso", "No")
                val days = listOf(day1, day2, day3, day4, day5, day6, day7)
                trainingDays.addAll(days)
            }
        }

        return trainingDays
    }


    private fun loadProgramSummary(preferences: Preferences) {

        val myAdapter = SummaryRecyclerViewAdapter(preferences)
        val recyclerView = binding.programSummary

        Log.i("TAG", "Se ha creado el RecyclerView")
        recyclerView.apply {
            //Aquí configuramos de qué forma se visualizan las vistas
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = myAdapter
        }

    }

    private fun goToApp() {
        val intent = Intent(context, AppActivity::class.java)
        Toast.makeText(context, "Accediendo a la app desde review!", Toast.LENGTH_LONG).show()
        startActivity(intent)
        activity?.finish()
    }


}

