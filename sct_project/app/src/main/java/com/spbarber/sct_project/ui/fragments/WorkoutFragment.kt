package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spbarber.sct_project.databinding.FragmentWorkoutBinding

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentWorkoutBinding.inflate(layoutInflater)

        /*if (getAuth().currentUser == null){
            val intent = Intent(requireContext(), MainActivity::class.java)
            Toast.makeText(context, "Volviendo al init fragment", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWorkoutBinding.inflate(layoutInflater)

        return binding.root
    }


}
