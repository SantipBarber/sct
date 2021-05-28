package com.spbarber.sct_project.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spbarber.sct_project.databinding.ItemProgressRecyclerViewBinding

class ProgressRecyclerViewAdapter() :
    RecyclerView.Adapter<ProgressRecyclerViewAdapter.ViewHolder>() {
    val items = mutableListOf<String>()

    fun newItems(items: String){
        this.items.add(items)
        notifyItemInserted(this.items.size)
    }
    class ViewHolder private constructor(val binding: ItemProgressRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root){



        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}