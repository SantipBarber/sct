package com.spbarber.sct_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.spbarber.sct_project.databinding.ItemTrainingDayListViewBinding
import com.spbarber.sct_project.entities.TrainingDay

class ListViewAdapter(context: Context, val layout: Int, private val trainingDays: List<TrainingDay>) :
    ArrayAdapter<TrainingDay>(context, layout, trainingDays) {

    //Esta función se invoca tantas veces como elementos tengamos en la lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(layout, null)
        }

        //val binding = ItemTrainingDayListViewBinding.bind(view!!)

        val trainingDay = trainingDays[position]
        //binding.tvNumDayListView.text = trainingDay.numDay.toString()
        //binding.tvExerciseDayListView.text = trainingDay.exercise
        /*val runAccessories = "Accesorios"
        val notAccessories = ""
        if (!trainingDay.accessories){
            binding.tvExtraListView.text = notAccessories
        } else {
            binding.tvExtraListView.text = runAccessories
        }*/


        return view!!
    }
}