package com.spbarber.sct_project.ui.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentWorkoutBinding
import com.spbarber.sct_project.entities.Athlete
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.Program
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import com.spbarber.sct_project.viewmodels.UserViewModel

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private val TAG = "TAG"
    private val modelAthlete: AthleteViewModel by viewModels()
    private val modelUserViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentWorkoutBinding.inflate(layoutInflater)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWorkoutBinding.inflate(layoutInflater)

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
                        modelAthlete.getAthlete(athlete!!)
                            .observe(viewLifecycleOwner, { athleteData ->


                                createSpinnerSelectWeek(
                                    athleteData.programs[0],
                                    athleteData,
                                    preferences
                                )
                                createSpinnerSelectDay(
                                    athleteData.programs[0],
                                    athleteData,
                                    preferences
                                )
                            })
                    })
            })



        return binding.root
    }


    private fun createSpinnerSelectDay(
        program: Program,
        athleteData: Athlete,
        preferences: Preferences
    ) {
        val listDay = arrayListOf<String>()
        listDay.add("D??a")
        program.trainingDays.forEach { day ->
            listDay.add("D??a ${day.numDay}")
        }
        val spinnerSelectDay: Spinner = binding.spinnerWorkoutDay
        ArrayAdapter(
            requireContext(),
            R.layout.custom_spinner_item,
            listDay
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSelectDay.adapter = arrayAdapter
        }
        spinnerSelectDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                createWorkout(
                    binding.spinnerWorkoutWeek.selectedItemPosition,
                    position,
                    athleteData,
                    preferences
                )
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun createSpinnerSelectWeek(
        program: Program,
        athleteData: Athlete,
        preferences: Preferences
    ) {
        val listWeek = arrayListOf<String>()
        listWeek.add("Semana")
        program.weeks.forEach { week ->
            listWeek.add(week.title)
        }
        val spinnerSelectWeek: Spinner = binding.spinnerWorkoutWeek
        ArrayAdapter(
            requireContext(),
            R.layout.custom_spinner_item,
            listWeek
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSelectWeek.adapter = arrayAdapter
        }

        spinnerSelectWeek.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                createWorkout(
                    position,
                    binding.spinnerWorkoutDay.selectedItemPosition,
                    athleteData,
                    preferences
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun createWorkout(week: Int, day: Int, athleteData: Athlete, preferences: Preferences) {
        val arrayDay = day - 1
        val arrayWeek = week - 1

        if (week == 0) {
            Log.i(TAG, "Debes seleccionar la semana")
            Log.i(TAG, day.toString())
            //binding.tvWorkoutDay.text = "D??a: ${ athleteData.programs[0].trainingDays[day].numDay}"
            //binding.tvWorkoutMovement.text = "${ athleteData.programs[0].trainingDays[day].exercise}"
        } else if (day == 0) {
            Log.i(TAG, "Debes seleccionar el d??a")
            Log.i(TAG, week.toString())
        } else {
            Log.i(TAG, "Ya podemos implementar el c??digo")
            Log.i(TAG, week.toString())
            Log.i(TAG, day.toString())
            binding.llWorkout1.visibility = View.VISIBLE
            binding.llWorkout2.visibility = View.VISIBLE
            binding.llWorkout3.visibility = View.VISIBLE
            binding.llWorkout4.visibility = View.VISIBLE
            binding.tvSelectDate.visibility = View.GONE
            binding.tvWorkoutDay.text =
                "D??a ${athleteData.programs[0].trainingDays[arrayDay].numDay}"
            binding.tvWorkoutMovement.text = athleteData.programs[0].trainingDays[arrayDay].exercise


            val intensityWeek = athleteData.programs[0].weeks[arrayWeek].intensity / 100
            var weightForExercise = 0.0F
            val exercise = athleteData.programs[0].trainingDays[arrayDay].exercise
            when (exercise) {
                "Sentadilla" -> weightForExercise = intensityWeek * athleteData.records[0].weight
                "Press banca" -> weightForExercise = intensityWeek * athleteData.records[1].weight
                "Peso muerto" -> weightForExercise = intensityWeek * athleteData.records[2].weight
                "Sentadilla variante" -> weightForExercise =
                    (intensityWeek * athleteData.records[0].weight) / 1.4F
                "Press banca variante" -> weightForExercise =
                    (intensityWeek * athleteData.records[1].weight) / 1.4F
                "Peso muerto variante" -> weightForExercise =
                    (intensityWeek * athleteData.records[2].weight) / 1.4F
                "Descanso" -> weightForExercise = 0.0F
                "Cardio" -> weightForExercise = 0.0F
                else -> {
                    Snackbar.make(
                        binding.root,
                        "No se puede generar el Workout Day",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            val weight = String.format("%.2f", weightForExercise)
            if (weightForExercise.toInt() != 0) {
                binding.tvWorkoutSets.text =
                    "Sets: ${athleteData.programs[0].weeks[arrayWeek].numberOfSets}"
                binding.tvWorkoutWeight.text = "- Peso: $weight kg"
                binding.tvWorkoutAccesories.text =
                    "- Accesorios: ${athleteData.programs[0].trainingDays[arrayDay].accessories}"
                binding.tvWorkoutRest.text =
                    "Descanso entre clusters: ${athleteData.programs[0].weeks[arrayWeek].restBetweenCluster}"
                binding.tvWorkoutSetDuration.text =
                    "Duraci??n de cada set: ${athleteData.programs[0].weeks[arrayWeek].setDuration}"
            } else {
                binding.tvWorkoutAccesories.textAlignment = View.TEXT_ALIGNMENT_CENTER
                binding.tvWorkoutWeight.textAlignment = View.TEXT_ALIGNMENT_CENTER
                binding.tvWorkoutSets.text = "Opcionalmente ????????"
                binding.tvWorkoutReps.text = ""
                binding.tvWorkoutWeight.text = "Realizar el calentamiento"
                binding.tvWorkoutAccesories.text = "Movilidad"
                binding.tvWorkoutRest.text = "Y peque??a sesi??n de cardio"
                binding.tvWorkoutSetDuration.text = ""

            }

            if (athleteData.programs[0].trainingDays[arrayDay].exercise == "Descanso" || athleteData.programs[0].trainingDays[arrayDay].exercise == "Cardio") {
                binding.tvWorkoutReps.text = ""
            } else {


                val repsForSet =
                    athleteData.programs[0].weeks[week].totalReps / athleteData.programs[0].weeks[week].numberOfSets
                Log.i(TAG, repsForSet.toString())
                when (repsForSet) {
                    3 -> {
                        binding.tvWorkoutReps.text = "1/1/1 reps "
                    }
                    6 -> {
                        binding.tvWorkoutReps.text = "2/2/1/1 reps "
                    }
                    7 -> {
                        binding.tvWorkoutReps.text = "3/2/2 reps "
                    }
                    else -> {
                        binding.tvWorkoutReps.text = "???"
                        Snackbar.make(
                            binding.root,
                            "La combinaci??n de reps y sets no es v??lida",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }

            modelAthlete.getAthlete(athleteData.nameAthlete).observe(viewLifecycleOwner, {
                Log.i(TAG, "${it.programs[0].trainingDays[arrayDay].trainingDone[arrayWeek]}")
                if (!it.programs[0].trainingDays[arrayDay].trainingDone[arrayWeek]) {
                    binding.tvIconCheck.compoundDrawables.getOrNull(2)?.setTint(Color.RED)
                    binding.btnTrainDone.setOnClickListener {
                        binding.tvIconCheck.compoundDrawables.getOrNull(2)?.setTint(Color.GREEN)
                        //Log.i(TAG, "Cambiando color")
                        //Hay que modificar en la base de datos el valor de trainingDone

                        modelAthlete.updateDoneTraining(athleteData, arrayDay, arrayWeek)
                    }

                } else {
                    binding.tvIconCheck.compoundDrawables.getOrNull(2)?.setTint(Color.GREEN)
                }
            })



        }


    }
}
