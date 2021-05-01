package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding


class SigninFormFragment : Fragment() {
    private lateinit var binding: FragmentSigninFormBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSigninFormBinding.inflate(layoutInflater)


        return binding.root
    }
}