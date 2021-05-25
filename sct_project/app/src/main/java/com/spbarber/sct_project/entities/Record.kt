package com.spbarber.sct_project.entities

import java.util.*

data class Record(
    val dateRecord: Date,
    val weight: Float,
    val idExercise: String
){
    constructor(): this(Date(System.currentTimeMillis()),0.0F, "" )

    override fun toString(): String {
        return super.toString()
    }
}
