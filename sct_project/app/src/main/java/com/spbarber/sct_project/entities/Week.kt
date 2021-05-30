package com.spbarber.sct_project.entities

data class Week(
    val idWeek: Int,
    val title: String,
    val intensity: Float,
    val totalVolume: Float,
    val squatVolume: Float,
    val pressVolume: Float,
    val deadliftVolume: Float,
    val restBetweenCluster: Float,
    val setDuration: Int,
    val numberOfSets: Int,
    val totalReps: Int
) {
    constructor() : this(0, "SEMANA", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1, 5, 0)
}