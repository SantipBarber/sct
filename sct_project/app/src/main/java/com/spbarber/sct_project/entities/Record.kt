package com.spbarber.sct_project.entities

import java.util.*

data class Record(
    val athlete: Athlete,
    val exercise: Exercise,
    val dateRecord: Date,
    val weight: Float
)
