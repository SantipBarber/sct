package com.spbarber.sct_project.entities

data class ProgramDetail(
    val program: Program,
    val exercise: Exercise,
    val sets: Int,
    val reps: Int,
    val restBetweenSets: Int,
    val restBetweenClusters: Int,
    val weightForRep: Float
)
