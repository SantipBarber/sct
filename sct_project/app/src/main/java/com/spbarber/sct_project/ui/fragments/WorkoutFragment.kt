package com.spbarber.sct_project.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.spbarber.sct_project.App
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentWorkoutBinding
import com.spbarber.sct_project.listeners.MainListener
import com.spbarber.sct_project.ui.activities.MainActivity

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutBinding
    private var listener: MainListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutBinding.inflate(layoutInflater)


        return binding.root
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = getAuth().currentUser
        val nameUser = currentUser?.email
        if(currentUser != null){
            Snackbar.make(binding.root, "El usuario logueado es $nameUser", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MainActivity
        listener!!.showBottomNavigation()
    }

}
