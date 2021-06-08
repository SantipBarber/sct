package com.spbarber.sct_project.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.spbarber.sct_project.App
import com.spbarber.sct_project.databinding.FragmentStatsBinding
import com.spbarber.sct_project.entities.Athlete
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import com.spbarber.sct_project.viewmodels.UserViewModel
import kotlin.math.absoluteValue
import kotlin.math.exp


class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private val modelAthlete: AthleteViewModel by viewModels()
    private val modelUser: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsBinding.inflate(layoutInflater)

        modelUser.getPreferences(App.getAuth().uid.toString())
            .observe(viewLifecycleOwner, { preferencesData ->
                /**
                 * Obtenemos el atleta que tiene asociado el usuario
                 */
                modelAthlete.getAthlete(preferencesData.name.toString())
                    .observe(viewLifecycleOwner, {athleteData ->
                        /**
                         * Obtenemos los datos del atleta en uso
                         */
                        val lineChart = binding.chart
                        createGraph(athleteData, lineChart)

                        //Rellenamos los datos de la tabla RM
                        Log.i("TAG", preferencesData.experience.toString())

                        binding.tvLlRmTableSquat1.text = athleteData.records[0].idExercise.capitalize()
                        binding.tvLlRmTableSquat2.text = athleteData.records[0].weight.toInt().toString()
                        binding.tvLlRmTableSquat3.text = getNewRecord(athleteData, preferencesData.experience.toString(),0)

                        binding.tvLlRmTablePress1.text = athleteData.records[1].idExercise.capitalize()
                        binding.tvLlRmTablePress2.text = athleteData.records[1].weight.toInt().toString()
                        binding.tvLlRmTablePress3.text = getNewRecord(athleteData, preferencesData.experience.toString(),1)

                        binding.tvLlRmTableDeadlift1.text = athleteData.records[2].idExercise.capitalize()
                        binding.tvLlRmTableDeadlift2.text = athleteData.records[2].weight.toInt().toString()
                        binding.tvLlRmTableDeadlift3.text = getNewRecord(athleteData, preferencesData.experience.toString(),2)
                    })
            })
        return binding.root
    }

    private fun getNewRecord(athleteData: Athlete, experience: String, position: Int): String {
        var newRecord = ""
        when(experience){
            "beginner" -> {
                val r = athleteData.records[position].weight * 1.10
                newRecord = r.absoluteValue.toInt().toString()
            }
            "intermediate" -> {
                val r = athleteData.records[position].weight * 1.05
                newRecord = r.absoluteValue.toInt().toString()
            }
            "advanced" -> {
                val r = athleteData.records[position].weight * 1.025
                newRecord = r.absoluteValue.toInt().toString()
            }
        }
        return newRecord
    }

    private fun createGraph(athleteData: Athlete, lineChart: LineChart) {
        val intensityData = mutableListOf<Entry>()
        val volumeData = mutableListOf<Entry>()

        athleteData.programs.forEach { program ->
            program.weeks.forEach { week ->
                val idWeek = week.idWeek.toFloat()
                val intensity = week.intensity
                val entryDataIntensity = Entry(idWeek, intensity)
                intensityData.add(entryDataIntensity)

                val volume = week.totalVolume
                val entryDataVolume= Entry(idWeek, volume)
                volumeData.add(entryDataVolume)

            }
        }

        val lineDataIntensity = LineDataSet(intensityData, "Intensidad (% RM)")
        val lineDataVolume = LineDataSet(volumeData, "Volumen (kg)")

        lineDataIntensity.color = Color.RED
        lineDataIntensity.setDrawValues(false)
        lineDataIntensity.yMin
        lineDataIntensity.setDrawCircles(true)
        lineDataIntensity.setCircleColor(Color.RED)
        lineDataIntensity.lineWidth = 3F
        lineDataIntensity.valueTextSize = 10F
        lineDataIntensity.setDrawFilled(true)
        lineDataIntensity.fillColor = Color.RED
        lineDataIntensity.axisDependency = YAxis.AxisDependency.RIGHT
        lineDataIntensity.enableDashedLine(10F, 5F, 0F)
        lineDataIntensity.enableDashedHighlightLine(10F, 5F, 0F)

        lineDataVolume.color = Color.BLUE
        lineDataVolume.setDrawValues(false)
        lineDataVolume.setDrawCircles(true)
        lineDataVolume.setCircleColor(Color.BLUE)
        lineDataVolume.lineWidth = 3F
        lineDataVolume.valueTextSize = 10F
        lineDataVolume.setDrawFilled(true)
        lineDataVolume.fillColor = Color.BLUE
        lineDataVolume.axisDependency = YAxis.AxisDependency.LEFT
        lineDataVolume.enableDashedLine(10F, 5F, 0F)
        lineDataVolume.enableDashedHighlightLine(10F, 5F, 0F)

        val lineData = LineData(lineDataIntensity, lineDataVolume)

        lineChart.data = lineData
        lineChart.invalidate()
    }
}