package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentSiginBinding
import com.spbarber.sct_project.databinding.FragmentSigninFormBinding
import com.spbarber.sct_project.entities.User
import com.spbarber.sct_project.utils.Constants
import java.util.*
import java.util.regex.Pattern

class SigninFragment : Fragment() {
    private lateinit var binding: FragmentSiginBinding
    private val TAG = "TAG"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSiginBinding.inflate(layoutInflater)
        //Bindeo del formulario para el registro
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
        Log.i(
            "TAG",
            "$experience $goal $duration $days $frequency $rmSquat $rmPress $rmDeadlift $nameAthlete $heigth $weight $genre $birthdate"
        )

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
                experience,
                goal,
                duration,
                days,
                frequency!!,
                rmSquat!!,
                rmPress!!,
                rmDeadlift!!
            )
            NavHostFragment.findNavController(this).navigate(action)
        }

        //Recogemos todos las preferencias del usuario
        val userPreferences = mutableListOf(
            experience,
            goal,
            duration,
            days,
            frequency,
            rmSquat,
            rmPress,
            rmDeadlift,
            nameAthlete,
            heigth,
            weight,
            genre,
            birthdate
        )


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

            getAuth().createUserWithEmailAndPassword(
                username.getInputText(),
                password.getInputText()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        getFirestore()
                            .collection(Constants.USERS)
                            .document(getAuth().currentUser!!.uid)
                            .set(
                                User(
                                    username.getInputText(),
                                    firstName.getInputText(),
                                    surnames.getInputText(),
                                    password.getInputText(),
                                    Date(System.currentTimeMillis()),
                                    Date(System.currentTimeMillis())
                                )
                            )
                            .addOnCompleteListener { taskNewUser ->
                                if (taskNewUser.isSuccessful) {
                                    val action = SigninFragmentDirections
                                        .actionSiginFragmentToReviewAndConfirmFragment(
                                            experience,
                                            goal,
                                            duration,
                                            days,
                                            frequency!!,
                                            rmSquat!!,
                                            rmPress!!,
                                            rmDeadlift!!,
                                            nameAthlete,
                                            heigth!!,
                                            weight!!,
                                            genre,
                                            birthdate,
                                            firstName.getInputText(),
                                            surnames.getInputText(),
                                            username.getInputText(),
                                            password.getInputText()
                                        )
                                    NavHostFragment.findNavController(this).navigate(action)
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        "No se ha podido crear el usuario",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                    } else {
                        when (task.exception) {
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
                            else -> {
                                Snackbar.make(
                                    binding.root,
                                    "No se ha podido realizar el registro",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
        }

        return binding.root
    }

    private fun TextInputEditText.getInputText(): String {
        return text.toString()
    }


}

