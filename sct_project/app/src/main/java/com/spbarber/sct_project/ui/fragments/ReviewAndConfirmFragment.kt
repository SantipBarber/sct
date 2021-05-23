package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.databinding.FragmentReviewAndConfirmBinding
import com.spbarber.sct_project.viewmodels.UsuarioViewModel

class ReviewAndConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReviewAndConfirmBinding
    private val model: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewAndConfirmBinding.inflate(layoutInflater)

        val preferences = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).preferences
        }

        loadPersonalData(
            preferences?.name.toString(),
            preferences?.birthdate.toString(),
            preferences?.genre.toString(),
            preferences?.heigth.toString(),
            preferences?.weight.toString(),
            preferences?.experience.toString(),
            preferences?.goal.toString(),
            preferences?.days.toString(),
            preferences?.frequency.toString()
        )

        binding.btnBack.setOnClickListener {
            val action =
                ReviewAndConfirmFragmentDirections.actionReviewAndConfirmFragmentToSiginFragment(
                    preferences
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        //Generar programa
        val user = getAuth().currentUser?.uid

        binding.btnGenerate.setOnClickListener {
            model.getUser(user.toString()).observe(viewLifecycleOwner, {
                //print(it.toString())
                //Log.i("TAG", "Mensaje $it")
                it.let {
                    binding.tvReviewName.setText(it?.lastName)
                }
            })
//            model.getPreferences(user.toString()).observe(viewLifecycleOwner, { prefs ->
//                print(prefs.toString())
//            })
        }


        return binding.root
    }

    private fun loadPersonalData(
        name: String,
        birthdate: String,
        genre: String,
        heigth: String,
        weight: String,
        experience: String,
        goal: String,
        days: String,
        frequency: String
    ) {
        binding.tvReviewName.text = "Atleta: $name"
        //Hay que calcular la edad
        binding.tvReviewAge.text = "Edad: $birthdate"
        binding.tvReviewGenre.text = "Género: $genre"
        binding.tvReviewHeigth.text = "Altura: $heigth"
        binding.tvReviewWeight.text = "Peso: $weight"
        binding.tvReviewExperience.text = experience
        binding.tvReviewGoal.text = goal
        binding.tvReviewDays.text = days
        binding.tvReviewFrequency.text = "$frequency x movimiento"

    }


}

