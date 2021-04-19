package com.spbarber.sct_project.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentLogoInfoBinding
import com.spbarber.sct_project.ui.activities.login


class LogoInfoFragment : Fragment() {
    private lateinit var binding: FragmentLogoInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogoInfoBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnToLogin.setOnClickListener {
            val intent = Intent(activity, login::class.java)
            Snackbar.make(view, "Vamos hacia el Login", Snackbar.LENGTH_LONG).show()
            startActivity(intent)
        }

        binding.btnStartSignin.setOnClickListener {

        }

        return view
    }


}