package com.spbarber.sct_project.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.databinding.FragmentReviewAndConfirmBinding
import com.spbarber.sct_project.entities.Athlete
import com.spbarber.sct_project.utils.Constants

class ReviewAndConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReviewAndConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewAndConfirmBinding.inflate(layoutInflater)

        val experience = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).experience
        }
        val goal = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).goal
        }
        val duration = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).duration
        }
        val days = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).days
        }
        val frequency = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).frequency
        }
        val rmSquat = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).rmSquat
        }
        val rmPress = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).rmPress
        }
        val rmDeadlift = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).rmDeadlift
        }
        val nameAthlete = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).name
        }
        val heigth = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).heigth
        }
        val weight = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).weight
        }
        val genre = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).genre
        }
        val birthdate = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).birthdate
        }
        val firstname = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).firstname
        }
        val lastname = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).surname
        }
        val username = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).username
        }
        val passwordUser = arguments?.let {
            ReviewAndConfirmFragmentArgs.fromBundle(it).password
        }
        Log.i(
            "TAG",
            "$experience $goal $duration $days $frequency $rmSquat $rmPress $rmDeadlift $nameAthlete $heigth $weight $genre $birthdate $firstname $lastname $username $passwordUser"
        )

        binding.btnBack.setOnClickListener {
            val action =
                ReviewAndConfirmFragmentDirections.actionReviewAndConfirmFragmentToSiginFragment(
                    experience,
                    goal,
                    duration,
                    days!!,
                    frequency!!,
                    rmSquat!!,
                    rmPress!!,
                    rmDeadlift!!,
                    nameAthlete,
                    heigth!!,
                    weight!!,
                    genre,
                    birthdate
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        val user = getAuth().currentUser
        binding.btnGenerate.setOnClickListener {
            val athlete = user?.uid?.let { it1 ->
                Athlete(
                    nameAthlete.toString(),
                    heigth.toString().toInt(),
                    weight.toString().toFloat(),
                    birthdate.toString(),
                    it1
                )
            }
            if (athlete != null) {
                getFirestore()
                    .collection(Constants.ATHLETES)
                    .document(getAuth().currentUser!!.uid)
                    .set(athlete)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.i("TAG", "Hemos creado el primer atleta")

                        } else {
                            Log.i("TAG", "no se ha creado el atleta")
                        }
                    }
            }

        }


        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewAndConfirmFragment().apply {

            }
    }
}