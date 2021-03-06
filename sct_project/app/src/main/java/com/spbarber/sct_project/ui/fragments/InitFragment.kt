package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentInitBinding


class InitFragment : Fragment() {
    private lateinit var binding: FragmentInitBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInitBinding.inflate(layoutInflater)
        val view = binding.root

        Log.i("TAG", App.getAuth().currentUser.toString())

        binding.btnToLogin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_initFragment_to_loginFragment)
        }

        binding.btnStartSignin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_initFragment_to_experienceFragment)
        }

        return view
    }
}