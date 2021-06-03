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
import com.spbarber.sct_project.viewmodels.TrainingDataViewModel
import java.util.*

class ReviewAndConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReviewAndConfirmBinding
    private val modelAthlete: AthleteViewModel by viewModels()
    private val modelTrainingData: TrainingDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            createAthlete(preferences)
            goToApp()
        }

        return binding.root
    }

    private fun createAthlete(preferences: Preferences) {
        modelTrainingData.getTrainingData(
            preferences.goal!!.capitalize(),
            getDuration(preferences.duration.toString())
        ).observe(viewLifecycleOwner, { dataTraining ->
            val weeksOfProgram = loadWeeks(dataTraining, preferences)
            val programs = newProgram(preferences, weeksOfProgram)
            val records = newRecords(preferences)

            val newAthlete = Athlete(
                preferences.name.toString(),
                preferences.heigth,
                preferences.weight,
                preferences.birthdate.toString(),
                preferences.genre.toString(),
                getAuth().currentUser?.uid.toString(),
                records,
                programs
            )
            modelAthlete.createAthlete(newAthlete).observe(viewLifecycleOwner, { exception ->
                when (exception) {
                    is FirebaseFirestoreException -> {
                        Log.i("TAG", "no se ha podido almacenar el atleta")
                    }
                    else -> {
                        Log.i("TAG", "Hemos almacenado al atleta correctamente!!!!")
                    }
                }
            })
        })
    }

    private fun newProgram(preferences: Preferences, weeks: List<Week>): List<Program> {
        val program = mutableListOf<Program>()
        val trainingDays = loadTrainingDays(preferences)
        val newProgram = Program(
            1,
            "Primer programa",
            Date(
                System.currentTimeMillis()
            ),
            Date(System.currentTimeMillis()),
            preferences.goal.toString(),
            preferences.duration.toString(),
            trainingDays,
            weeks
        )
        program.add(newProgram)

        return program
    }

    private fun newRecords(preferences: Preferences): List<Record> {
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

        return records
    }

    private fun loadWeeks(dataTraining: TrainingData, preferences: Preferences): MutableList<Week> {
        val durationWeeks = getDuration(preferences.duration.toString())
        val delimiter = " "
        val numberOfWeeks = durationWeeks.split(delimiter)
        val weeks = mutableListOf<Week>()

        dataTraining.trainingData.forEach {
            var intensityWeek = it.value.initIntensity.toFloat()
            var totalReps = it.value.initReps
            var squatVolume = intensityWeek / 100 * preferences.rmSquat * totalReps
            var pressVolume = intensityWeek / 100 * preferences.rmPress * totalReps
            var deadliftVolume = intensityWeek / 100 * preferences.rmDeadlift * totalReps
            var totalVolume = squatVolume + pressVolume + deadliftVolume
            var restBetweenClusters = it.value.restBetClusters.toFloat()
            val setDuration = when (preferences.goal) {
                "Size" -> 5
                "Improve Endurance" -> 7
                "Maximal Strength" -> 10
                else -> 10
            }
            var totalSets = it.value.initSets

            for (i in 1..numberOfWeeks[0].toInt()) {
                val week = Week(
                    i,
                    "Semana $i",
                    intensityWeek,
                    totalVolume,
                    squatVolume,
                    pressVolume,
                    deadliftVolume,
                    restBetweenClusters,
                    setDuration,
                    totalSets,
                    totalReps
                )
                weeks.add(week)
                when (it.value.modFactor) {
                    2 -> {
                        when (numberOfWeeks[0].toInt()) {
                            6 -> {
                                if (i > 1 && i % 2 == 0) {
                                    if (preferences.goal == "Maximal Strength") {
                                        intensityWeek *= it.value.intensityIncrease.toFloat()
                                        totalReps -= 3
                                        squatVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        pressVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        deadliftVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        totalVolume =
                                            squatVolume + pressVolume + deadliftVolume
                                        totalSets -= it.value.setDecrease.toInt()
                                        restBetweenClusters *= it.value.restIncrease.toFloat()
                                    } else {
                                        intensityWeek *= it.value.intensityIncrease.toFloat()
                                        totalReps -= 6
                                        squatVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        pressVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        deadliftVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        totalVolume =
                                            squatVolume + pressVolume + deadliftVolume
                                        totalSets -= it.value.setDecrease.toInt()
                                        restBetweenClusters *= it.value.restIncrease.toFloat()
                                    }
                                }
                            }
                            8 -> {
                                if (i > 1 && i % 2 == 0) {
                                    if (preferences.goal == "Maximal Strength") {
                                        intensityWeek *= it.value.intensityIncrease.toFloat()
                                        totalReps -= 3
                                        squatVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        pressVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        deadliftVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        totalVolume =
                                            squatVolume + pressVolume + deadliftVolume
                                        totalSets -= it.value.setDecrease.toInt()
                                        restBetweenClusters *= it.value.restIncrease.toFloat()
                                    } else {
                                        intensityWeek *= it.value.intensityIncrease.toFloat()
                                        totalReps -= 6
                                        squatVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        pressVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        deadliftVolume =
                                            intensityWeek / 100 * preferences.rmSquat * totalReps
                                        totalVolume =
                                            squatVolume + pressVolume + deadliftVolume
                                        totalSets -= it.value.setDecrease.toInt()
                                        restBetweenClusters *= it.value.restIncrease.toFloat()
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        if (i > 1 && i % 3 == 0) {
                            if (preferences.goal == "Maximal Strength") {
                                intensityWeek *= it.value.intensityIncrease.toFloat()
                                totalReps -= 3
                                squatVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                pressVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                deadliftVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                totalVolume = squatVolume + pressVolume + deadliftVolume
                                totalSets -= it.value.setDecrease.toInt()
                                restBetweenClusters *= it.value.restIncrease.toFloat()
                            } else {
                                intensityWeek *= it.value.intensityIncrease.toFloat()
                                totalReps -= 6
                                squatVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                pressVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                deadliftVolume =
                                    intensityWeek / 100 * preferences.rmSquat * totalReps
                                totalVolume =
                                    squatVolume + pressVolume + deadliftVolume
                                totalSets -= it.value.setDecrease.toInt()
                                restBetweenClusters *= it.value.restIncrease.toFloat()
                            }
                        }
                    }
                }
            }
        }
        return weeks
    }

    private fun getDuration(duration: String): String {
        return when (duration) {
            "6 semanas" -> "6 weeks"
            "8 semanas" -> "8 weeks"
            "10 semanas" -> "10 weeks"
            "12 semanas" -> "12 weeks"
            else -> "6 weeks"
        }
    }

    private fun loadTrainingDays(preferences: Preferences): List<TrainingDay> {
        val trainingDays = mutableListOf<TrainingDay>()
        val listOfTrainingDays = mutableListOf<Boolean>()
        val duration = preferences.duration.toString()
        val delimiter = " "
        val numberOfWeeks = duration.split(delimiter)

        for (item in 0..numberOfWeeks[0].toInt()) {
            listOfTrainingDays.add(false)
        }

        when (preferences.days.toString()) {
            "3 días" -> {
                val day1 = TrainingDay(1, "Sentadilla", "Si", listOfTrainingDays)
                val day2 = TrainingDay(2, "Cardio", "Opcional", listOfTrainingDays)
                val day3 = TrainingDay(3, "Press banca", "Si", listOfTrainingDays)
                val day4 = TrainingDay(4, "Descanso", "No", listOfTrainingDays)
                val day5 = TrainingDay(5, "Peso muerto", "Si", listOfTrainingDays)
                val day6 = TrainingDay(6, "Cardio", "Opcional", listOfTrainingDays)
                val day7 = TrainingDay(7, "Descanso", "No", listOfTrainingDays)
                val days = listOf(day1, day2, day3, day4, day5, day6, day7)
                trainingDays.addAll(days)

            }
            "5 días" -> {

            }
            "6 días" -> {
                val day1 = TrainingDay(1, "Sentadilla", "No", listOfTrainingDays)
                val day2 = TrainingDay(2, "Peso muerto variante", "Si", listOfTrainingDays)
                val day3 = TrainingDay(3, "Press banca", "No", listOfTrainingDays)
                val day4 = TrainingDay(4, "Sentadilla variante", "Si", listOfTrainingDays)
                val day5 = TrainingDay(5, "Peso muerto", "No", listOfTrainingDays)
                val day6 = TrainingDay(6, "Press banca variante", "Si", listOfTrainingDays)
                val day7 = TrainingDay(7, "Descanso", "No", listOfTrainingDays)
                val days = listOf(day1, day2, day3, day4, day5, day6, day7)
                trainingDays.addAll(days)
            }
        }

        return trainingDays
    }

    private fun loadProgramSummary(preferences: Preferences) {

        val myAdapter = SummaryRecyclerViewAdapter(preferences)
        val recyclerView = binding.programSummary
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

