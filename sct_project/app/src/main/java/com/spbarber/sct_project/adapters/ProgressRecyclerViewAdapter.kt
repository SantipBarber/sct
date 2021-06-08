package com.ºspbarber.sct_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spbarber.sct_project.databinding.ItemProgressRecyclerViewBinding
import com.spbarber.sct_project.entities.Week

class ProgressRecyclerViewAdapter(private val items: List<Week>) :
    RecyclerView.Adapter<ProgressRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder private constructor(
        val binding: ItemProgressRecyclerViewBinding,
        progressRecyclerViewAdapter: ProgressRecyclerViewAdapter
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillData(items: Week) {
            binding.tvRvWeek.text = "Semana ${items.idWeek}"
            binding.tvRvPercentRm.text = "${items.intensity.toInt()} %"
            binding.tvRvTotalVolume.text = "${items.totalVolume.toInt()} kg"
            binding.tvRvSquatVolume.text = "${items.squatVolume.toInt()} kg"
            binding.tvRvBenchPressVolume.text = "${items.pressVolume.toInt()} kg"
            binding.tvRvDeadliftVolume.text = "${items.deadliftVolume.toInt()} kg"
        }

        companion object {
            fun newInstance(
                parent: ViewGroup,
                progressRecyclerViewAdapter: ProgressRecyclerViewAdapter
            ): ProgressRecyclerViewAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemProgressRecyclerViewBinding.inflate(layoutInflater, parent, false)
                return ProgressRecyclerViewAdapter.ViewHolder(binding, progressRecyclerViewAdapter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.newInstance(parent, this)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.fillData(items[position])

    override fun getItemCount(): Int = items.size
}
