package com.spbarber.sct_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbarber.sct_project.entities.Athlete

class AthleteViewModel : ViewModel() {
    private val TAG = "TAG"
    //BY LAZY inicializa la recuperación de datos sin hacerlo nostros
    private val athletes: MutableLiveData<List<Athlete>> by lazy {
        MutableLiveData<List<Athlete>>().also {
            loadAthletes()
        }
    }

    private fun loadAthletes() {
        TODO("Not yet implemented")
    }

    fun getAthletes(): LiveData<List<Athlete>> {
        return athletes
    }


}