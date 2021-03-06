package com.spbarber.sct_project.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSigninBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding
import com.spbarber.sct_project.databinding.MyProgressBarBinding
import com.spbarber.sct_project.entities.*
import com.spbarber.sct_project.viewmodels.UserViewModel
import java.time.LocalDate
import java.time.Period
import java.util.*
import java.util.regex.Pattern

class SigninFragment : Fragment() {
    private lateinit var binding: FragmentSigninBinding
    private lateinit var bindingForm: FragmentSigninFormBinding
    private lateinit var bindingProgressBar: MyProgressBarBinding

    private val model: UserViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(layoutInflater)
        bindingProgressBar = MyProgressBarBinding.inflate(layoutInflater)

        //Bindeo del formulario para el registro
        bindingForm = FragmentSigninFormBinding.bind(binding.scrollviewSignin.rootView)

        val preferences = arguments?.let {
            SigninFragmentArgs.fromBundle(it).preferences
        }

        binding.btnGoogleLogin.setOnClickListener {
            if (binding.btnMailLogin.isVisible) {
                binding.btnMailLogin.isVisible = false
            }
        }

        binding.btnMailLogin.setOnClickListener {
            if (binding.btnGoogleLogin.isVisible) {
                binding.btnGoogleLogin.isVisible = false
                binding.scrollviewSignin.isVisible = true
                binding.btnMailLogin.isVisible = false
            }
        }

        binding.btnBack.setOnClickListener {
            val action = SigninFragmentDirections.actionSiginFragmentToPersonalDataFragment(
                preferences
            )
            NavHostFragment.findNavController(this).navigate(action)
        }

        bindingForm.tietName.addTextChangedListener {
            if (it.isNullOrBlank()) {
                bindingForm.tilNameUser.error = "Introduce un nombre v??lido"
            } else {
                bindingForm.tilNameUser.error = ""
                bindingForm.tilNameUser.helperText = ""
            }
        }
        bindingForm.tietName.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) bindingForm.tilNameUser.error = ""
            }
        }
        bindingForm.tietSurnames.addTextChangedListener {
            if (it.isNullOrBlank()) {
                bindingForm.tilSurnamesUser.error = "Introduce un apellido"
            } else {
                bindingForm.tilSurnamesUser.error = ""
                bindingForm.tilSurnamesUser.helperText = ""
            }
        }
        bindingForm.tietSurnames.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) bindingForm.tilSurnamesUser.error = ""
            }
        }
        bindingForm.tietUser.addTextChangedListener {
            if (it.isNullOrBlank()) {
                bindingForm.tilUser.error = "Introduce tu email"
            } else {
                bindingForm.tilUser.error = ""
                bindingForm.tilUser.helperText = ""
            }
        }
        bindingForm.tietUser.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) bindingForm.tilUser.error = ""
            }
        }
        bindingForm.tietPassword.addTextChangedListener {
            if (it.isNullOrBlank()) {
                bindingForm.tilPassword.error = "Introduce una contrase??a v??lida"
            } else {
                bindingForm.tilPassword.error = ""
                bindingForm.tilPassword.helperText = ""
            }
        }
        bindingForm.tietPassword.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    bindingForm.tietPassword.error = ""
                    bindingForm.tietPassword.text?.clear()
                }
            }
        }
        bindingForm.tietConfirmPassword.addTextChangedListener {
            if (it.isNullOrBlank()) {
                bindingForm.tilConfirmPassword.error = "Debes repetir la contrase??a"
            } else {
                bindingForm.tilConfirmPassword.error = ""
                bindingForm.tilConfirmPassword.helperText = ""
            }
        }
        bindingForm.tietPassword.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    bindingForm.tietPassword.error = ""
                    bindingForm.tietConfirmPassword.text?.clear()
                }
            }
        }

        binding.btnSignin.setOnClickListener {
            //Para indicar que est?? cargando
            bindingProgressBar.myProgressBar.visibility = View.VISIBLE
            val firstName = bindingForm.tietName
            val surnames = bindingForm.tietSurnames
            val username = bindingForm.tietUser
            val password = bindingForm.tietPassword
            val confirmPassword = bindingForm.tietConfirmPassword

            val attrUser = listOf(firstName, surnames, username, password, confirmPassword)
            var error = false
            attrUser.forEach {
                if (it.getInputText().isBlank()) {
                    error = true
                    when (it.id) {
                        R.id.tiet_name -> {
                            bindingForm.tilNameUser.error = "Introduce un nombre  v??lido"
                        }
                        R.id.tiet_surnames -> {
                            bindingForm.tilSurnamesUser.error = "Introduce al menos un apellido"
                        }
                        R.id.tiet_user -> {
                            bindingForm.tilUser.error = "Introduce el nombre del atleta"
                        }
                        R.id.tiet_password -> {
                            bindingForm.tilPassword.error = "Debes introducir una contrase??a"
                        }
                        R.id.tiet_confirm_password -> {
                            bindingForm.tilConfirmPassword.error =
                                "Debes volver a introducir la contrase??a"
                        }
                    }
                }
                if (error) return@setOnClickListener
                val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                val emailMatcher = emailPattern.matcher(username.getInputText())
                val validEmail = emailMatcher.matches()
                if (!validEmail) {
                    bindingForm.tilUser.error = "Introduce un formato de email v??lido"
                    return@setOnClickListener
                }
                if (password.getInputText().length < 6) {
                    bindingForm.tilPassword.error =
                        "La contrase??a debe contener al menos 6 caracteres"
                    return@setOnClickListener
                }
                if (!password.getInputText().equals(confirmPassword.getInputText())) {
                    bindingForm.tilPassword.error = "Las contrase??as no son iguales"
                    bindingForm.tilConfirmPassword.error = "Las contrase??as no son iguales"
                    return@setOnClickListener
                }

            }
            //preferences?.firstname = firstName.getInputText()
            //preferences?.surname = surnames.getInputText()
            //preferences?.username = username.getInputText()
            //preferences?.password = password.getInputText()
            //preferences?.confirmPassword = confirmPassword.getInputText()

            val user = User(
                firstName.getInputText(),
                surnames.getInputText(),
                username.getInputText(),
                password.getInputText(),
                Date(System.currentTimeMillis()),
                Date(System.currentTimeMillis())
            )

            //Registro por Firebase
            model.signin(user, preferences!!)
                .observe(viewLifecycleOwner, { exception ->
                    if (exception == null) {
                        //Log.i("TAG", "Entra en el IF...")
                        goToProgramGenerator(
                            preferences
                        )
                    } else {
                        bindingProgressBar.myProgressBar.visibility = View.GONE
                        //Log.d("TAG", exception.toString())
                        when (exception) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                Snackbar.make(
                                    binding.root,
                                    R.string.email_already_exist,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthWeakPasswordException -> {
                                Snackbar.make(
                                    binding.root,
                                    "La contrase??a es muy corta",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthInvalidUserException -> {
                                Snackbar.make(
                                    binding.root,
                                    "No se ha podido realizar el registro",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            is FirebaseFirestoreException -> {
                                Snackbar.make(
                                    binding.root,
                                    "No se ha podido a??adir la colecci??n a Firebase",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                Snackbar.make(
                                    binding.root,
                                    "El nombre de usuario ya existe. Debes introducir otro para registrarte.",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                })

        }

        return binding.root
    }

    private fun goToProgramGenerator(
        preferences: Preferences
    ) {
        val action = SigninFragmentDirections
            .actionSiginFragmentToReviewAndConfirmFragment(
                preferences
            )
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun TextInputEditText.getInputText(): String {
        return text.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(preferences: Preferences): Int {
        val birthdate = preferences.birthdate.toString()
        val listDate = birthdate.split("/").toTypedArray()
        println(listDate.contentToString())

        val ageAthlete = Period.between(
            LocalDate.of(listDate[2].toInt(), listDate[1].toInt(), listDate[0].toInt()),
            LocalDate.now()
        ).years

        println(ageAthlete.toString())

        return ageAthlete
    }

}

