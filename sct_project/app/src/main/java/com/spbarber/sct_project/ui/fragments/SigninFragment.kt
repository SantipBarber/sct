package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding

class SigninFragment : Fragment() {
    private lateinit var binding: FragmentSiginBinding
    private val TAG = "TAG"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSiginBinding.inflate(layoutInflater)
        val bindingForm = FragmentSigninFormBinding.bind(binding.scrollviewSignin.rootView)

        val experience = arguments?.let {
            SigninFragmentArgs.fromBundle(it).experience
        }
        val goal = arguments?.let {
            SigninFragmentArgs.fromBundle(it).goal
        }
        val duration = arguments?.let {
            SigninFragmentArgs.fromBundle(it).duration
        }
        val days = arguments?.let {
            SigninFragmentArgs.fromBundle(it).days
        }
        val frequency = arguments?.let {
            SigninFragmentArgs.fromBundle(it).frequency
        }
        val rmSquat = arguments?.let {
            SigninFragmentArgs.fromBundle(it).rmSquat
        }
        val rmPress = arguments?.let {
            SigninFragmentArgs.fromBundle(it).rmPress
        }
        val rmDeadlift = arguments?.let {
            SigninFragmentArgs.fromBundle(it).rmDeadlift
        }
        val nameAthlete = arguments?.let {
            SigninFragmentArgs.fromBundle(it).name
        }
        val heigth = arguments?.let {
            SigninFragmentArgs.fromBundle(it).heigth
        }
        val weight = arguments?.let {
            SigninFragmentArgs.fromBundle(it).weight
        }
        val genre = arguments?.let {
            SigninFragmentArgs.fromBundle(it).genre
        }
        val birthdate = arguments?.let {
            SigninFragmentArgs.fromBundle(it).birthdate
        }

        //Recogemos todos las preferencias del usuario
        val userPreferences = mutableListOf(experience, goal, duration, days, frequency, rmSquat, rmPress, rmDeadlift, nameAthlete, heigth, weight, genre, birthdate)


        bindingForm.tietName.addTextChangedListener{
            if (it.isNullOrBlank()){
                bindingForm.tilNameUser.error = "Introduce un nombre válido"
            } else {
                bindingForm.tilNameUser.error = ""
                bindingForm.tilNameUser.helperText = ""
            }
        }
        bindingForm.tietName.setOnFocusChangeListener { _, hasFocus ->
            run{
                if (hasFocus) bindingForm.tilNameUser.error = ""
            }
        }
        bindingForm.tietSurnames.addTextChangedListener{
            val text = it as TextInputEditText
            if (text.text!!.isNullOrBlank()){
                bindingForm.tilSurnamesUser.error = "Introduce un apellido"
            } else {
                bindingForm.tilSurnamesUser.error = ""
                bindingForm.tilSurnamesUser.helperText = ""
            }
        }
        bindingForm.tietSurnames.setOnFocusChangeListener { _, hasFocus ->
            run{
                if (hasFocus) bindingForm.tilSurnamesUser.error = ""
            }
        }
        bindingForm.tietUser.addTextChangedListener{
            val text = it as TextInputEditText
            if (text.text!!.isNullOrBlank()){
                bindingForm.tilUser.error = "Introduce un nombre de atleta"
            } else {
                bindingForm.tilUser.error = ""
                bindingForm.tilUser.helperText = ""
            }
        }
        bindingForm.tietUser.setOnFocusChangeListener { _, hasFocus ->
            run{
                if (hasFocus) bindingForm.tilUser.error = ""
            }
        }
        bindingForm.tietPassword.addTextChangedListener{
            val text = it as TextInputEditText
            if (text.text!!.isNullOrBlank()){
                bindingForm.tilPassword.error = "Introduce una contraseña válida"
            } else {
                bindingForm.tilPassword.error = ""
                bindingForm.tilPassword.helperText = ""
            }
        }
        bindingForm.tietPassword.setOnFocusChangeListener { _, hasFocus ->
            run{
                if (hasFocus) bindingForm.tietPassword.error = ""
            }
        }
        bindingForm.tietConfirmPassword.addTextChangedListener{
            val text = it as TextInputEditText
            if (text.text!!.isNullOrBlank()){
                bindingForm.tilConfirmPassword.error = "Debes repetir la contraseña"
            } else {
                bindingForm.tilConfirmPassword.error = ""
                bindingForm.tilConfirmPassword.helperText = ""
            }
        }
        bindingForm.tietPassword.setOnFocusChangeListener { _, hasFocus ->
            run{
                if (hasFocus) bindingForm.tietPassword.error = ""
            }
        }
        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_siginFragment_to_personalDataFragment)
        }

        binding.btnSignin.setOnClickListener {
            val firstName = bindingForm.tietName
            val surnames = bindingForm.tietSurnames
            val nameAthlete = bindingForm.tietUser
            val password = bindingForm.tietPassword
            val confirmPassword = bindingForm.tietConfirmPassword

            val attrUser = listOf(firstName, surnames, nameAthlete, password, confirmPassword)
            var error = false;
            attrUser.forEach {
                if (it.getInputText().isBlank()){
                    error = true
                    when(it.id){
                        R.id.tiet_name -> {
                            bindingForm.tilNameUser.error = "Introduce un nombre  válido"
                        }
                        R.id.tiet_name -> {
                            bindingForm.tilSurnamesUser.error = "Introduce al menos un apellido"
                        }
                        R.id.tiet_user -> {
                            bindingForm.tilUser.error = "Introduce el nombre del atleta"
                        }
                        R.id.tiet_password -> {
                            bindingForm.tilPassword.error = "Debes introducir una contraseña"
                        }
                        R.id.tiet_confirm_password -> {
                            bindingForm.tilConfirmPassword.error = "Debes volver a introducir la contraseña"
                        }
                    }
                }
                if (error) return@setOnClickListener
                if (password.getInputText().length < 6) {
                    bindingForm.tilPassword.error = "La contraseña debe contener al menos 6 caracteres"
                    return@setOnClickListener
                }
                if (password.getInputText().equals(confirmPassword.getInputText())){
                    bindingForm.tilPassword.error = "Las contraseñas no son iguales"
                    bindingForm.tilConfirmPassword.error = "Las contraseñas no son iguales"
                    return@setOnClickListener
                }

            }
            userPreferences.forEach {
                println(it)
            }
            attrUser.forEach {
                println(it)
            }
        }

        return binding.root
    }
    fun TextInputEditText.getInputText(): String{
        return text.toString()
    }

}

