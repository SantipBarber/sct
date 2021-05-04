package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding

class SiginFragment : Fragment() {
    private lateinit var binding: FragmentSiginBinding
    private val TAG = "TAG"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSiginBinding.inflate(layoutInflater)
        val bindingForm = FragmentSigninFormBinding.bind(binding.scrollviewSignin.rootView)

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_siginFragment_to_personalDataFragment)
        }

        binding.btnSignin.setOnClickListener {
            //surnames = binding.tilSurnamesUser.editText?.text.toString()
            //firstName = binding.tilNameUser.editText?.text.toString()
            //username = binding.tilUser.editText?.text.toString()
            //password = binding.tilPassword.editText?.text.toString()
            //Hacer comprobación de los campos y de que la contraseña coincida en los dos input

        }





        return binding.root
    }


}