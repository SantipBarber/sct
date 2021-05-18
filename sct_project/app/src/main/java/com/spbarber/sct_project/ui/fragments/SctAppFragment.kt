package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSctAppBinding

class SctAppFragment : Fragment() {
    private lateinit var binding: FragmentSctAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSctAppBinding.inflate(layoutInflater)



        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            NavHostFragment.findNavController(this).navigate(R.id.action_sctAppFragment_to_initFragment)
        }

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

}
