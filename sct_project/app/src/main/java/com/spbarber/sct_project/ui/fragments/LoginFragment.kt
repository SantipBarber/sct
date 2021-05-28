package com.spbarber.sct_project.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentLoginBinding
import com.spbarber.sct_project.databinding.MyProgressBarBinding
import com.spbarber.sct_project.ui.activities.AppActivity
import com.spbarber.sct_project.viewmodels.TrainingDataViewModel
import com.spbarber.sct_project.viewmodels.UsuarioViewModel
import java.util.regex.Pattern


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var bindingProgressBar: MyProgressBarBinding
    private val model: UsuarioViewModel by viewModels()
    private val model2: TrainingDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.btnGoogleLogin.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        bindingProgressBar = MyProgressBarBinding.inflate(layoutInflater)

        binding.mainInputUsername.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.inputUser.error = "Introduce un usuario válido"
            } else {
                binding.inputUser.error = ""
            }
        }
        binding.mainInputUsername.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.inputUser.error = ""
                    binding.mainInputUsername.text?.clear()
                }
            }
        }
        binding.mainInputPassword.addTextChangedListener {
            val size = it!!.length
            if (size < 6) {
                binding.inputPassword.error = "Caracter $size/6"
            } else {
                binding.inputPassword.error = ""
            }
        }
        binding.mainInputPassword.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.inputPassword.error = ""
                    binding.mainInputPassword.text?.clear()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.mainInputUsername
            val pass = binding.mainInputPassword

            val attrsLogin = listOf(username, pass)
            var error = false
            attrsLogin.forEach {
                if (it.getInputText().isBlank()) {
                    error = true
                    when (it.id) {
                        R.id.main_input_username -> {
                            binding.inputUser.error = "Introduce un nombre de usuario válido"
                        }
                        R.id.main_input_password -> {
                            binding.inputPassword.error = "Debes introducir la contraseña"
                        }
                    }
                }
            }
            if (error) return@setOnClickListener
            val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
            val emailMatcher = emailPattern.matcher(username.getInputText())
            val validEmail = emailMatcher.matches()
            if (!validEmail) {
                binding.inputUser.error = "Introduce un formato de email válido"
                return@setOnClickListener
            }
            if (pass.length() < 6) {
                binding.inputPassword.error = "La contraseña debe contener al menos 6 caracteres"
                return@setOnClickListener
            }

            //Para indicar que está cargando
            bindingProgressBar.myProgressBar.visibility = View.VISIBLE
            //Acceso por Firebase

            model.login(username.getInputText(), pass.getInputText())
                .observe(viewLifecycleOwner, { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        model._users.observe(viewLifecycleOwner, {

                        })
                        goToApp()
                    } else {
                        bindingProgressBar.myProgressBar.visibility = View.GONE
                        Log.d("TAG", task.exception.toString())
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                Snackbar.make(
                                    binding.root,
                                    "Debes registrarte para acceder",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                Snackbar.make(
                                    binding.root,
                                    "Error al iniciar sesión",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                })
        }

        binding.btnLoginBack.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToInitFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    private fun goToApp() {
        val intent = Intent(context, AppActivity::class.java)
        //Toast.makeText(context, "Accediendo a la app", Toast.LENGTH_LONG).show()
        startActivity(intent)
        activity?.finish()
    }

    private fun TextInputEditText.getInputText(): String {
        return text.toString()
    }
}