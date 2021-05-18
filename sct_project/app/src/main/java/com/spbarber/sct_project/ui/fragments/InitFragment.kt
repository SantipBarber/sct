package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentInitBinding


class InitFragment : Fragment() {
    private lateinit var binding: FragmentInitBinding
    private val auth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInitBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnToLogin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_initFragment_to_loginFragment)
        }

        binding.btnStartSignin.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_initFragment_to_experienceFragment)
        }

        return view
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val action = InitFragmentDirections.actionInitFragmentToSctAppFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

}