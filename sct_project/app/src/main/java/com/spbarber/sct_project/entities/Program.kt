package com.spbarber.sct_project.entities

import java.util.*

data class Program(
    val idProgram: Long,
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val goal: String,
)
