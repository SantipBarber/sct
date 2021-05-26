package com.spbarber.sct_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spbarber.sct_project.databinding.ItemTrainingSummaryRecyclerViewBinding
import com.spbarber.sct_project.entities.TrainingDay

class SummaryRecyclerViewAdapter(private val trainingDays: List<TrainingDay>) : RecyclerView.Adapter<SummaryRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder private constructor(val binding: ItemTrainingSummaryRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillData(trainingDay: TrainingDay) {
            binding.tvNumDayListView.text = trainingDay.numDay.toString()
            binding.tvExerciseDayListView.text = trainingDay.exercise
            binding.tvExtraListView.text = "Extras"
            /*val runAccessories = "Accesorios"
            val notAccessories = ""
            if (!trainingDay.accessories){
                binding.tvExtraListView.text = notAccessories
            } else {
                binding.tvExtraListView.text = runAccessories
            }*/
        }

        companion object {
            fun newInstance(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemTrainingSummaryRecyclerViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.fillData(trainingDays[position])

    override fun getItemCount() = trainingDays.size
}