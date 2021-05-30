package com.spbarber.sct_project.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.spbarber.sct_project.databinding.FragmentStatsBinding
import kotlin.system.measureTimeMillis


class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsBinding.inflate(layoutInflater)

        createGraph()


        return binding.root
    }

    private fun createGraph() {
        val lineChart = binding.chart

        var revenueComp1 = mutableListOf<Entry>()
        revenueComp1.add(Entry(1F, 1000.0F))
        revenueComp1.add(Entry(2F, 20000F))
        revenueComp1.add(Entry(3F,30000F))
        revenueComp1.add(Entry(4F, 40000F))
        val revenueComp2 = mutableListOf<Entry>()
        revenueComp2.add(Entry(1F,12000F))
        revenueComp2.add(Entry( 2F,23000F))
        revenueComp2.add(Entry(3F, 35000F))
        revenueComp2.add(Entry(4F, 48000f))

        val lineDataSet1 = LineDataSet(revenueComp1, "Company1")
        val lineDataSet2 = LineDataSet(revenueComp2, "Company2")

        lineDataSet1.color = Color.RED
        lineDataSet1.setDrawValues(false)
        lineDataSet1.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet2.color = Color.BLUE
        lineDataSet2.setDrawValues(false)
        lineDataSet2.axisDependency = YAxis.AxisDependency.LEFT

        val lineDataSets = LineData(lineDataSet1, lineDataSet2)

        lineChart.data = lineDataSets

    }

}