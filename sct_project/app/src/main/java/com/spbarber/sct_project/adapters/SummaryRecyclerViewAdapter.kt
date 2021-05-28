package com.spbarber.sct_project.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.spbarber.sct_project.databinding.ItemTrainingSummaryRecyclerViewBinding
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.TrainingDay
import java.time.LocalDate
import java.time.Period

class SummaryRecyclerViewAdapter(private val preferences: Preferences) :
    RecyclerView.Adapter<SummaryRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder private constructor(val binding: ItemTrainingSummaryRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun fillData(preferences: Preferences) {

            binding.tvReviewName.text = preferences.name.toString()
            val age = getAge(preferences).toString()
            println(age)
            binding.tvReviewAge.text = "Edad: $age"
            if (preferences.genre.toString() == "man") {
                binding.tvReviewGenre.text = "Hombre"
            } else {
                binding.tvReviewGenre.text = "Mujer"
            }
            binding.tvReviewHeigth.text = "Altura: ${preferences.heigth.toString()} cm"
            binding.tvReviewWeight.text = "Peso: ${preferences.weight.toString()} kg"
            when(preferences.experience.toString()){
                "beginner" -> binding.tvReviewExperience.text = "Principiante"
                "intermediate" -> binding.tvReviewExperience.text = "Intermedio"
                "advanced" -> binding.tvReviewExperience.text = "Avanzado"
            }
            binding.tvReviewGoal.text = preferences.goal.toString()
            binding.tvReviewDuration.text = preferences.duration.toString()
            binding.tvReviewDays.text = preferences.days.toString()
            binding.tvReviewFrequency.text = "${preferences.frequency.toString()}x"

            loadTrainingDays(preferences)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getAge(preferences: Preferences): Int {
            val birthdate = preferences.birthdate.toString()
            val listDate = birthdate.split("/").toTypedArray()
            println(listDate.contentToString())

            val ageAthlete = Period.between(
                LocalDate.of(listDate[2].toInt(), listDate[1].toInt(), listDate[0].toInt()),
                LocalDate.now()
            ).years

            println(ageAthlete.toString())

            return ageAthlete
        }

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemTrainingSummaryRecyclerViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun loadTrainingDays(preferences: Preferences) {
            val days = preferences.days.toString()
            Log.i("TAG", days)
            when(days) {
                "3 días" -> {
                    binding.tvSummaryWeekDayOne.text = "Día 1"
                    binding.tvSummaryWeekDayTwo.text = "Día 2"
                    binding.tvSummaryWeekDayThree.text = "Día 3"
                    binding.tvSummaryWeekDayFour.text = "Día 4"
                    binding.tvSummaryWeekDayFive.text = "Día 5"
                    binding.tvSummaryWeekDaySix.text = "Día 6"
                    binding.tvSummaryWeekDaySeven.text = "Día 7"

                    binding.tvExerciseDayOne.text = "Sentadilla"
                    binding.tvExerciseDayTwo.text = "Cardio"
                    binding.tvExerciseDayThree.text = "Press banca"
                    binding.tvExerciseDayFour.text = "Descanso"
                    binding.tvExerciseDayFive.text = "Peso muerto"
                    binding.tvExerciseDaySix.text = "Cardio"
                    binding.tvExerciseDaySeven.text = "Descanso"

                    binding.tvAccesoriesDayOne.text = "Si"
                    binding.tvAccesoriesDayTwo.text = "Opcional"
                    binding.tvAccesoriesDayThree.text = "Si"
                    binding.tvAccesoriesDayFour.text = "No"
                    binding.tvAccesoriesDayFive.text = "Si"
                    binding.tvAccesoriesDaySix.text = "Opcional"
                    binding.tvAccesoriesDaySeven.text = "No"
                }
                "4 días" -> {

                }
                "5 días" -> {

                }
                "6 días" -> {
                    binding.tvSummaryWeekDayOne.text = "Día 1"
                    binding.tvSummaryWeekDayTwo.text = "Día 2"
                    binding.tvSummaryWeekDayThree.text = "Día 3"
                    binding.tvSummaryWeekDayFour.text = "Día 4"
                    binding.tvSummaryWeekDayFive.text = "Día 5"
                    binding.tvSummaryWeekDaySix.text = "Día 6"
                    binding.tvSummaryWeekDaySeven.text = "Día 7"

                    binding.tvExerciseDayOne.text = "Sentadilla"
                    binding.tvExerciseDayTwo.text = "Peso muerto variante"
                    binding.tvExerciseDayThree.text = "Press banca"
                    binding.tvExerciseDayFour.text = "Sentadilla variante"
                    binding.tvExerciseDayFive.text = "Peso muerto"
                    binding.tvExerciseDaySix.text = "Press banca variante"
                    binding.tvExerciseDaySeven.text = "Descanso"

                    binding.tvAccesoriesDayOne.text = "No"
                    binding.tvAccesoriesDayTwo.text = "Si"
                    binding.tvAccesoriesDayThree.text = "No"
                    binding.tvAccesoriesDayFour.text = "Si"
                    binding.tvAccesoriesDayFive.text = "No"
                    binding.tvAccesoriesDaySix.text = "Si"
                    binding.tvAccesoriesDaySeven.text = "No"
                }
                else -> {
                    Log.i("TAG", "No se ha podido realizar el resumen. Opción no válida.")
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.newInstance(parent)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.fillData(preferences)

    override fun getItemCount() = 1

}