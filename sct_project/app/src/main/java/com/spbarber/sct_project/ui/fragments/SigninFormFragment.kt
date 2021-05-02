package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding

var surnames = ""
var firstName = ""
var username = ""
var password = ""
class SigninFormFragment : Fragment() {
    private lateinit var binding: FragmentSigninFormBinding
    private val TAG = "TAG"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSigninFormBinding.inflate(layoutInflater)

        /*binding.btnConfirmData.setOnClickListener {
            surnames = binding.tilSurnamesUser.editText?.text.toString()
            firstName = binding.tilNameUser.editText?.text.toString()
            username = binding.tilUser.editText?.text.toString()
            password = binding.tilPassword.editText?.text.toString()
            //Hacer comprobación de los campos y de que la contraseña coincida en los dos input


            Log.i(TAG, experienceUser.toString())
            Log.i(TAG, goalUser.toString())
            Log.i(TAG, durationProgram.toString())
            Log.i(TAG, trainingDaysProgram.toString())
            Log.i(TAG, frequencyMovement.toString())
            Log.i(TAG, rmSquatuser.toString())
            Log.i(TAG, rmPressUser.toString())
            Log.i(TAG, rmDeadliftUser.toString())
            Log.i(TAG, nameAthlete)
            Log.i(TAG, heigthAthlete)
            Log.i(TAG, weigthAthlete)
            Log.i(TAG, genreAthlete)
            Log.i(TAG, birthdateAthlete)
            Log.i(TAG, surnames)
            Log.i(TAG, firstName)
            Log.i(TAG, username)
            Log.i(TAG, password)
        }*/
        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance(surnamesUser: String, firstNameUser: String, usernameUser: String, passwordUser: String){
            SigninFormFragment().apply {
                arguments?.apply {
                    putString(surnamesUser, surnames)
                    putString(firstNameUser, firstName)
                    putString(usernameUser, username)
                    putString(passwordUser, password)
                }
            }
        }
    }

}