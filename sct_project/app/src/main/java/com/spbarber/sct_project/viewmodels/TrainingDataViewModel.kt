package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.VarsTraining
import com.spbarber.sct_project.models.TrainingData
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingDataViewModel : ViewModel() {
    private val TAG = "TAG"
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
                    .document("Size")
                    .get()
                    .addOnCompleteListener { document ->
                        Log.i(TAG, "${document.result}")
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
                //val data = documents.data
                val hashMap = documents.data
                //hashMap?.put("6 weeks", documents.data!!.get("6 weeks"))
                //hashMap?.put("8 weeks", documents.data!!.get("8 weeks"))
                //hashMap?.put("10 weeks", documents.data!!.get("10 weeks"))
                //hashMap?.put("12 weeks", documents.data!!.get("12 weeks"))

                hashMap?.put(duration, documents.data!!.get(duration))
                val data = Gson().toJson(hashMap)
                val vars= Gson().fromJson(data, Map::class.java)

                //val six = Gson().fromJson(vars.get("6 weeks").toString(), VarsTraining::class.java)
                //val eigth = Gson().fromJson(vars.get("8 weeks").toString(), VarsTraining::class.java)
                //val ten = Gson().fromJson(vars.get("10 weeks").toString(), VarsTraining::class.java)
                //val twelve = Gson().fromJson(vars.get("12 weeks").toString(), VarsTraining::class.java)
                val varsByDuration = Gson().fromJson(vars.get(duration).toString(), VarsTraining::class.java)
                val trainingData = TrainingData()
                //trainingData.trainingData.put("6 weeks", six)
                //trainingData.trainingData.put("8 weeks", eigth)
                //trainingData.trainingData.put("10 weeks", ten)
                //trainingData.trainingData.put("12 weeks", twelve)
                trainingData.trainingData.put(duration, varsByDuration)
                Log.i(TAG, "${trainingData}")
                dataTraining.value = trainingData
            }
        return dataTraining

    }
}