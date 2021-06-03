package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.ktx.toObject
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.Athlete
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AthleteViewModel : ViewModel() {
    private val TAG = "TAG"
    //BY LAZY inicializa la recuperación de datos sin hacerlo nostros
    private val _athletes: MutableLiveData<List<Athlete>> by lazy {
        MutableLiveData<List<Athlete>>().also {
            Log.i(TAG, "Cargando atletas del usuario")
            loadAthlete()
        }
    }

    private fun loadAthlete() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
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

    fun getAthlete(nameAthlete:String): LiveData<Athlete> {
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

    fun createAthlete(athlete: Athlete): LiveData<Exception?>{
        //Log.i(TAG, "Se está creando el nuevo atleta")
        val data = MutableLiveData<Exception?>()
        getFirestore()
            .collection(Constants.ATHLETES)
            .document(athlete.nameAthlete)
            .set(athlete)
            .addOnCompleteListener { taskNewAthlete ->
                if (taskNewAthlete.isSuccessful){
                    //Log.i(TAG, "El atleta ha sido almacenado")
                    data.value = null
                } else {
                    Log.i(TAG, "No se ha podido crear. Ver excecpción")
                    data.value = taskNewAthlete.exception
                }
            }
        return data

    }

    fun updateTrainingDone(athlete: Athlete, week: Int, arrayDay: Int): LiveData<Exception?>{
        val data = MutableLiveData<Exception?>()

        getFirestore()
            .collection(Constants.ATHLETES)
            .document(athlete.nameAthlete)
            .update(mapOf("programs.0.trainingDays.$arrayDay.trainingDone.$week" to true)).addOnCompleteListener {
                if (it.isSuccessful) {
                    data.value = null
                } else {
                    Log.i(TAG, "No se ha podido crear. Ver excecpción")
                    data.value = it.exception
                }
            }
        return data
    }


}