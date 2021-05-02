package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding


class SiginFragment : Fragment() {
    private lateinit var binding: FragmentSiginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSiginBinding.inflate(layoutInflater)


        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_siginFragment_to_personalDataFragment)
        }






        return binding.root
    }


}