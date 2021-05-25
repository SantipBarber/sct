package com.spbarber.sct_project.models

import com.spbarber.sct_project.entities.VarsTraining

data class TrainingData(val trainingData: MutableMap<String, VarsTraining>) {
    constructor(): this(trainingData = mutableMapOf())

    override fun toString(): String {
        return trainingData.toString()
    }
}
