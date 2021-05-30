package com.spbarber.sct_project.entities

import java.util.*

data class Program(
    val idProgram: Long,
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val goal: String,
    val duration: String,
    val trainingDays: List<TrainingDay>,
    val weeks: List<Week>
){
    constructor(): this(-1, "", Date(System.currentTimeMillis()), Date(System.currentTimeMillis()),"", "", mutableListOf(), mutableListOf())
}
