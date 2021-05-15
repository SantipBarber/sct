package com.spbarber.sct_project.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentPersonalDataBinding
import java.util.*
import java.util.regex.Pattern

class PersonalDataFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentPersonalDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPersonalDataBinding.inflate(layoutInflater)

        val experience = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).experience
        }
        val goal = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).goal
        }
        val duration = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).duration
        }
        val days = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).days
        }
        val frequency = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).frequency
        }
        val rmSquat = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).rmSquat
        }
        val rmPress = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).rmPress
        }
        val rmDeadlift = arguments?.let {
            PersonalDataFragmentArgs.fromBundle(it).rmdeadlift
        }
        Log.i("TAG", "$experience $goal $duration $days $frequency $rmSquat $rmPress $rmDeadlift")

        binding.tietAthleteName.addTextChangedListener {
            val size = it!!.length
            if (size < 2) {
                binding.tilAthleteName.helperText = ""
                binding.tilAthleteName.error = "Introduce un nombre válido"
            } else {
                binding.tilAthleteName.error = ""
                binding.tilAthleteName.helperText = ""
            }
        }
        binding.tietAthleteName.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilAthleteName.error = ""
                    binding.tietAthleteName.text?.clear()
                }
            }
        }

        binding.tietAtheteHeigth.addTextChangedListener {
            val size = it!!.toString().toInt()
            if (size < 130) {
                binding.tilAthleteHeigth.error = "Introduce una altura válida"
            } else {
                binding.tilAthleteHeigth.error = ""
                binding.tilAthleteHeigth.helperText = ""
            }
        }
        binding.tietAtheteHeigth.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilAthleteHeigth.error = ""
                    binding.tietAtheteHeigth.text?.clear()
                }

            }
        }

        binding.tietAthleteWeight.addTextChangedListener {
            val size = it!!.toString().toFloat()
            if (size < 40) {
                binding.tilAthleteWeight.error = "Introduce un peso válido"
            } else {
                binding.tilAthleteWeight.error = ""
                binding.tilAthleteWeight.helperText = ""
            }
        }
        binding.tietAthleteName.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilAthleteWeight.error = ""
                    binding.tietAthleteWeight.text?.clear()
                }
            }
        }

        binding.tietAthleteBirthdate.addTextChangedListener {
            val size = it!!.length
            if (size < 6) {
                binding.tilAthleteBirthdate.error = "Introduce un nombre válido"
            } else {
                binding.tilAthleteBirthdate.error = ""
                binding.tilAthleteBirthdate.helperText = ""
            }
        }
        binding.tietAthleteName.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    binding.tilAthleteBirthdate.error = ""
                    binding.tietAthleteBirthdate.text?.clear()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            val action = PersonalDataFragmentDirections.actionPersonalDataFragmentToRecordsFragment2(experience, goal, duration!!, days, frequency!!)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnPersonalDataNext.setOnClickListener {
            val nameAthlete = binding.tietAthleteName
            val heigthAthlete = binding.tietAtheteHeigth
            val weightAthlete = binding.tietAthleteWeight
            var genreAthlete = ""
            var genreAthleteID = binding.rgAthleteGender.checkedRadioButtonId
            when (genreAthleteID) {
                R.id.rb_genre_man -> genreAthlete = "man"
                R.id.rb_genre_woman -> genreAthlete = "woman"
            }
            val birthdateAthlete = binding.tietAthleteBirthdate

            val personalSettings = listOf(nameAthlete, heigthAthlete, weightAthlete, birthdateAthlete)
            var error = false
            personalSettings.forEach {
                if (it.getInputText().isBlank()){
                    error = true
                    when(it.id){
                        R.id.til_athlete_name -> {
                            binding.tietAthleteName.error = "Debes introducir el nombre del atleta"
                        }
                        R.id.til_athlete_heigth -> {
                            binding.tietAtheteHeigth.error = "Debes introducir la altura del atleta"
                        }
                        R.id.til_athlete_weight -> {
                            binding.tietAthleteWeight.error = "Debes introducir el peso del atleta"
                        }
                        R.id.til_athlete_birthdate -> {
                            binding.tietAthleteBirthdate.error = "Debes introducir la fecha de nacimiento"
                        }
                    }
                }
            }
            if (error) return@setOnClickListener
            if (nameAthlete.getInputText().length < 2 || nameAthlete.getInputText().length > 15){
                binding.tilAthleteName.error = "Introduce un nombre válido"
                return@setOnClickListener
            }
            if (heigthAthlete.getInputText().toInt() < 100 || heigthAthlete.getInputText().toInt() > 230){
                binding.tilAthleteWeight.error = "Introduce una altura válida"
                return@setOnClickListener
            }
            if (weightAthlete.getInputText().toFloat() < 40 || weightAthlete.getInputText().toFloat() > 200){
                binding.tilAthleteWeight.error = "Introduce un peso válido"
                return@setOnClickListener
            }
            val datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}")
            val dateMatcher = datePattern.matcher(birthdateAthlete.getInputText())
            val validDate = dateMatcher.matches()
            if (!validDate){
                binding.tilAthleteBirthdate.error = "Introduce un formato de fecha correcto"
                return@setOnClickListener
            }
            val action = PersonalDataFragmentDirections.actionPersonalDataFragmentToSiginFragment(
                    experience,
                    goal,
                    duration,
                    days,
                    frequency!!,
                    rmSquat!!,
                    rmPress!!,
                    rmDeadlift!!,
                    nameAthlete.getInputText(),
                    heigthAthlete.getInputText().toInt(),
                    weightAthlete.getInputText().toFloat(),
                    genreAthlete,
                    birthdateAthlete.getInputText()
            )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.tietAthleteBirthdate.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (hasFocus) {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(requireContext(), this, year, month, day)
                    datePicker.show()
                }
            }
        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        if (day < 10 && month <10){
            binding.tietAthleteBirthdate.setText("0$day/0${month + 1}/$year")
        } else if (day < 10) {
            binding.tietAthleteBirthdate.setText("0$day/${month + 1}/$year")
        } else if (month < 10){
            binding.tietAthleteBirthdate.setText("$day/0${month + 1}/$year")
        } else {
            binding.tietAthleteBirthdate.setText("$day/${month + 1}/$year")
        }

    }

    fun TextInputEditText.getInputText(): String {
        return text.toString()
    }


}

