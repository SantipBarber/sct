package com.spbarber.sct_project.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentPersonalDataBinding
import java.util.*

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

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_personalDataFragment_to_recordsFragment2)
        }

        binding.btnPersonalDataNext.setOnClickListener {
            val nameAthlete =  binding.tilAthleteName.editText?.text.toString()
            val heigthAthlete =  binding.tilAthleteHeight.editText?.text.toString().toInt()
            val weightAthlete =  binding.tilAthleteWeight.editText?.text.toString().toFloat()
            var genreAthlete = ""
            var genreAthleteID = binding.rgAthleteGender.checkedRadioButtonId
            when(genreAthleteID){
                R.id.rb_genre_man -> genreAthlete = "man"
                R.id.rb_genre_woman -> genreAthlete = "woman"
            }
            val birthdateAthlete = binding.tilAthleteBirthdate.editText?.text.toString()

            val action = PersonalDataFragmentDirections.actionPersonalDataFragmentToSiginFragment(
                    experience!!,
                    goal!!,
                    duration!!,
                    days!!,
                    frequency!!,
                    rmSquat!!,
                    rmPress!!,
                    rmDeadlift!!,
                    nameAthlete,
                    heigthAthlete,
                    weightAthlete,
                    genreAthlete,
                    birthdateAthlete
            )
            NavHostFragment.findNavController(this).navigate(action)
            Log.i("TAG", "$experience $goal $duration $days $frequency $rmSquat $rmPress $rmDeadlift $nameAthlete $heigthAthlete $weightAthlete $genreAthlete $birthdateAthlete")
        }

        binding.etAthleteBirthdate.setOnFocusChangeListener { _, _ ->
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            /*val picker = MaterialDatePicker.
                    .Builder
                    .datePicker()
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .build()*/

            var datePicker = DatePickerDialog(requireContext(), this, year, month, day)
            datePicker.show()
        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        binding.etAthleteBirthdate.setText("$day/${month + 1}/$year")
    }

}

