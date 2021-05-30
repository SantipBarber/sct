package com.spbarber.sct_project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.VarsTraining
import com.spbarber.sct_project.entities.TrainingData
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingDataViewModel : ViewModel() {
    val _dataTraining: MutableLiveData<TrainingData> by lazy {
        MutableLiveData<TrainingData>().also {
            loadTrainingData()
        }
    }

    private fun loadTrainingData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataTraining = TrainingData()
                getFirestore()
                    .collection(Constants.VARS)
                    .get()
                    .addOnCompleteListener { document ->
                        //Log.i("TAG", "${document.result}")
                    }
                _dataTraining.postValue(dataTraining)
            }
        }
    }

    fun getTrainingData(goal: String, duration: String): MutableLiveData<TrainingData> {
        val dataTraining = MutableLiveData<TrainingData>()
        getFirestore()
            .collection(Constants.VARS)
            .document(goal)
            .get()
            .addOnSuccessListener { documents ->
                val hashMap = documents.data
                hashMap?.put(duration, documents.data!![duration])
                //Log.i("TAG", hashMap.toString())
                val data = Gson().toJson(hashMap)
                val vars= Gson().fromJson(data, Map::class.java)

                val varsByDuration = Gson().fromJson(vars[duration].toString(), VarsTraining::class.java)
                val trainingData = TrainingData()

                trainingData.trainingData[duration] = varsByDuration
                dataTraining.value = trainingData
            }
        return dataTraining

    }
}