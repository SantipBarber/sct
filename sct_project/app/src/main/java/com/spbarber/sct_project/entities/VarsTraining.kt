package com.spbarber.sct_project.entities

import com.google.gson.annotations.SerializedName

data class VarsTraining(@SerializedName("final_intensity") val finalIntensity: Int,
                        @SerializedName("final_reps") val finalReps: Int,
                        @SerializedName("init_intensity") val initIntensity: Int,
                        @SerializedName("init_reps") val initReps: Int,
                        @SerializedName("intensity_increase") val intensityIncrease: Double,
                        @SerializedName("mod_factor") val modFactor: Int,
                        @SerializedName("rest_between_clusters") val restBetClusters: Int,
                        @SerializedName("rest_increase") val restIncrease: Double,
                        @SerializedName("set_decrease") val setDecrease: Double,
                        @SerializedName("init_sets") val initSets: Int)  {
    constructor(): this(0,0,0,0,0.0,0,0,0.0, 0.0, 5)
}