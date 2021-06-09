package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.spbarber.sct_project.App
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.Athlete
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.Program
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class AthleteViewModel : ViewModel() {
    private val TAG = "TAG"

    //BY LAZY inicializa la recuperación de datos sin hacerlo nosotros
    private val _athletes: MutableLiveData<List<Athlete>> by lazy {
        MutableLiveData<List<Athlete>>().also {
            Log.i(TAG, "Cargando atletas del usuario")
            loadAthlete()
        }
    }

    private fun loadAthlete() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val athletes = mutableListOf<Athlete>()
                getFirestore()
                    .collection(Constants.ATHLETES)
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.i(TAG, "${document.id} => ${document.data}")
                        }
                    }
                _athletes.postValue(athletes)
            }
        }
    }

    fun getAthlete(nameAthlete: String): LiveData<Athlete> {
        val data = MutableLiveData<Athlete>()
        getFirestore()
            .collection(Constants.ATHLETES)
            .document(nameAthlete)
            .get()
            .addOnSuccessListener { document ->
                val athlete = document.toObject<Athlete>()
                data.value = athlete!!
            }
        return data
    }

    fun createAthlete(athlete: Athlete, preferences: Preferences): LiveData<Exception?> {
        val data = MutableLiveData<Exception?>()
        getFirestore()
            .collection(Constants.ATHLETES)
            .document(athlete.nameAthlete)
            .set(athlete)
            .addOnCompleteListener { taskNewAthlete ->
                if (taskNewAthlete.isSuccessful) {
                    getFirestore()
                        .collection(Constants.USERS)
                        .document(App.getAuth().currentUser!!.uid)
                        .update("preferences", preferences)
                    data.value = null
                } else {
                    Log.i(TAG, "No se ha podido crear. Ver excepción")
                    data.value = taskNewAthlete.exception
                }
            }
        return data
    }


    fun updateDoneTraining(athlete: Athlete, arrayDay: Int, arrayWeek: Int) {
        val path = FieldPath.of("programs","0","trainingDays","$arrayDay","trainingDone","$arrayWeek")
        getFirestore()
            .collection(Constants.ATHLETES)
            .document(athlete.nameAthlete)
            .get()
            .addOnSuccessListener { document ->
                val list: ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<Boolean>>>>> = document.get("programs") as ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<Boolean>>>>>
                val map: HashMap<String, ArrayList<HashMap<String, ArrayList<Boolean>>>> = list[0]
                val training: ArrayList<HashMap<String, ArrayList<Boolean>>> = map["trainingDays"] as ArrayList<HashMap<String, ArrayList<Boolean>>>
                val done: HashMap<String, ArrayList<Boolean>> = training[arrayDay]
                val item: ArrayList<Boolean> = done["trainingDone"] as ArrayList<Boolean>
                item[arrayWeek] = true

                done["trainingDone"] = item
                training[arrayDay] = done
                map["trainingDays"] = training
                list[0] = map

                Log.i("TAG", list.toString())
                getFirestore()
                    .collection(Constants.ATHLETES)
                    .document(athlete.nameAthlete)
                    .update(mapOf(
                        "programs" to list
                    )).addOnSuccessListener {
                        Log.i("TAG", "Actualización completada")
                    }
            }



    }

}
