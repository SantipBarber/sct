package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingDataViewModel : ViewModel() {
    private val info = "TAG"
    val _dataTraining: MutableLiveData<Map<String, Any>> by lazy {
        MutableLiveData<Map<String, Any>>().also {
            loadTrainingData()
        }
    }

    private fun loadTrainingData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataTraining = mutableMapOf<String, Any>()
                getFirestore()
                    .collection(Constants.VARS)
                    .document("Size")
                    .get()
                    .addOnCompleteListener { document ->
                        Log.i(info, "${document.result}")
                    }
                _dataTraining.postValue(dataTraining)
            }
        }
    }

    /*fun getTrainingData(goal: String): LiveData<Map<String, Any>> {
        val dataTraining = MutableLiveData<Map<String, Any>>()
        getFirestore()
            .collection(Constants.VARS)
            .document(goal)
            .get()
            .addOnSuccessListener {
                val data = it.toObject<Map<String, Any>>()
                dataTraining.value = data!!
                Log.i(info, "Hemos lanzado al función del viewModel de TrainingData")
            }
        return dataTraining

    }*/
}