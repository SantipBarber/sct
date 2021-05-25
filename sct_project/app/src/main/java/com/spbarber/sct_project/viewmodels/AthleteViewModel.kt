package com.spbarber.sct_project.viewmodels

import android.provider.SyncStateContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spbarber.sct_project.App
import com.spbarber.sct_project.App.Companion.getAuth
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
    private val athletes: MutableLiveData<List<Athlete>> by lazy {
        MutableLiveData<List<Athlete>>().also {
            Log.i(TAG, "Cargando atletas del usuario")
            loadAthletes()
        }
    }

    private fun loadAthletes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                //val athletes = mutableListOf<com.google.firebase.firestore.a>()
            }
        }
    }

    fun getAthletes(): LiveData<List<Athlete>> {
        return athletes
    }

    fun createAthlete(athlete: Athlete): LiveData<Exception?>{
        Log.i(TAG, "Se está creando el nuevo atleta")
        val data = MutableLiveData<Exception?>()
        getFirestore()
            .collection(Constants.ATHLETES)
            .document()
            .set(athlete)
            .addOnCompleteListener { taskNewAthlete ->
                if (taskNewAthlete.isSuccessful){
                    Log.i(TAG, "El atleta ha sido almacenado")
                    data.value = null
                } else {
                    Log.i(TAG, "No se ha podido crear. Ver excecpción")
                    data.value = taskNewAthlete.exception
                }
            }
        return data

    }


}