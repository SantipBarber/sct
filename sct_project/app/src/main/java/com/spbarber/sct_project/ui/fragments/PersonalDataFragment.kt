package com.spbarber.sct_project.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.set
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.spbarber.sct_project.R
import com.spbarber.sct_project.databinding.FragmentPersonalDataBinding
import org.w3c.dom.Text
import java.util.*

var nameAthlete = ""
var heigthAthlete = ""
var weigthAthlete = ""
var genreAthlete = ""
var birthdateAthlete = ""
class PersonalDataFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentPersonalDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPersonalDataBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_personalDataFragment_to_recordsFragment)
        }

        binding.btnPersonalDataNext.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_personalDataFragment_to_siginFragment)
            
            nameAthlete =  binding.tilAthleteName.editText?.text.toString()
            heigthAthlete =  binding.tilAthleteHeight.editText?.text.toString()
            weigthAthlete =  binding.tilAthleteWeight.editText?.text.toString()
            var genreAthleteID = binding.rgAthleteGender.checkedRadioButtonId
            when(genreAthleteID){
                R.id.rb_genre_man -> genreAthlete = "man"
                R.id.rb_genre_woman -> genreAthlete = "woman"
            }
            birthdateAthlete = binding.tilAthleteBirthdate.editText?.text.toString()
        }

        binding.tilAthleteBirthdate.setEndIconOnClickListener{
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
        val dateSelect = "$day/${month + 1}/$year"
        //binding.etAthleteBirthdate.text.toString() = dateSelect
        //binding.etAthleteBirthdate.text = "$day/${month + 1}/$year"
    }


    companion object{
        @JvmStatic
        fun newInstance(name: String, height: Double, weigth: Double, genre: String, birthdate: String){
            PersonalDataFragment().apply {
                arguments?.apply {
                    putString(nameAthlete, name)
                    putDouble(heigthAthlete, height)
                    putDouble(weigthAthlete, weigth)
                    putString(genreAthlete, genre)
                    putString(birthdateAthlete, birthdate)
                }
            }
        }
    }
}

