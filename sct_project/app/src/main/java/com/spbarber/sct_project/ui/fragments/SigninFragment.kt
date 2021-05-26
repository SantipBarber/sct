package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.spbarber.sct_project.App
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSigninBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding
import com.spbarber.sct_project.databinding.MyProgressBarBinding
import com.spbarber.sct_project.entities.*
import com.spbarber.sct_project.viewmodels.AthleteViewModel
import com.spbarber.sct_project.viewmodels.UsuarioViewModel
import java.util.*
import java.util.regex.Pattern

class SigninFragment : Fragment() {
    private lateinit var binding: FragmentSigninBinding
    private lateinit var bindingForm: FragmentSigninFormBinding
    private lateinit var bindingProgressBar: MyProgressBarBinding

    private val model: UsuarioViewModel by viewModels()
    private val modelAthlete: AthleteViewModel by viewModels()
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
        //De momento no queremos que se vea el login con Google
        //binding.btnGoogleLogin.isVisible = false


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
                bindingForm.tilNameUser.error = "Introduce un nombre válido"
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
                bindingForm.tilPassword.error = "Introduce una contraseña válida"
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
                bindingForm.tilConfirmPassword.error = "Debes repetir la contraseña"
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
            //Para indicar que está cargando
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
                            bindingForm.tilNameUser.error = "Introduce un nombre  válido"
                        }
                        R.id.tiet_surnames -> {
                            bindingForm.tilSurnamesUser.error = "Introduce al menos un apellido"
                        }
                        R.id.tiet_user -> {
                            bindingForm.tilUser.error = "Introduce el nombre del atleta"
                        }
                        R.id.tiet_password -> {
                            bindingForm.tilPassword.error = "Debes introducir una contraseña"
                        }
                        R.id.tiet_confirm_password -> {
                            bindingForm.tilConfirmPassword.error =
                                "Debes volver a introducir la contraseña"
                        }
                    }
                }
                if (error) return@setOnClickListener
                val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
                val emailMatcher = emailPattern.matcher(username.getInputText())
                val validEmail = emailMatcher.matches()
                if (!validEmail) {
                    bindingForm.tilUser.error = "Introduce un formato de email válido"
                    return@setOnClickListener
                }
                if (password.getInputText().length < 6) {
                    bindingForm.tilPassword.error =
                        "La contraseña debe contener al menos 6 caracteres"
                    return@setOnClickListener
                }
                if (!password.getInputText().equals(confirmPassword.getInputText())) {
                    bindingForm.tilPassword.error = "Las contraseñas no son iguales"
                    bindingForm.tilConfirmPassword.error = "Las contraseñas no son iguales"
                    return@setOnClickListener
                }

            }
            preferences?.firstname = firstName.getInputText()
            preferences?.surname = surnames.getInputText()
            preferences?.username = username.getInputText()
            preferences?.password = password.getInputText()
            preferences?.confirmPassword = confirmPassword.getInputText()

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
                        Log.i("TAG", "Entra en el IF...")
                        goToProgramGenerator(
                            preferences
                        )
                    } else {
                        bindingProgressBar.myProgressBar.visibility = View.GONE
                        Log.d("TAG", exception.toString())
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
                                    "La contraseña es muy corta",
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
                                    "No se ha podido añadir la colección a Firebase",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                Snackbar.make(
                                    binding.root,
                                    "Algo no ha ido bien",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                })
            val records = mutableListOf<Record>()
            val idExerciseSquat = "squat"
            val idExercisePress = "benchpress"
            val idExerciseDeadlift = "deadlift"
            val recordSquat = Record(
                Date(System.currentTimeMillis()),
                preferences.rmSquat,
                idExerciseSquat
            )
            val recordPress = Record(
                Date(System.currentTimeMillis()),
                preferences.rmPress,
                idExercisePress
            )
            val recordDeadlift = Record(
                Date(System.currentTimeMillis()),
                preferences.rmDeadlift,
                idExerciseDeadlift
            )
            val recordsAthlete = listOf(recordSquat, recordPress, recordDeadlift)
            records.addAll(recordsAthlete)
            val programs = mutableListOf<Program>()
            val program1 = Program(
                1,
                "Primer programa",
                Date(
                    System.currentTimeMillis()
                ),
                Date(System.currentTimeMillis()),
                preferences.goal.toString()
            )
            programs.add(program1)
            val newAthlete = Athlete(
                preferences.name.toString(),
                preferences.heigth,
                preferences.weight,
                preferences.birthdate.toString(),
                preferences.genre.toString(),
                App.getAuth().currentUser?.uid.toString(),
                records,
                programs
            )
            modelAthlete.createAthlete(newAthlete).observe(viewLifecycleOwner, { exception ->
                when (exception) {
                    is FirebaseFirestoreException -> {
                        Log.i("TAG", "no se ha podido almaccenar el atleta")
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


}

