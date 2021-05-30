package com.spbarber.sct_project.entities

data class Athlete(
    val nameAthlete: String,
    val heigth: Int,
    val weight: Float,
    val birthdate: String,
    val genre: String,
    val idUser: String,
    val records: List<Record>,
    val programs: List<Program>,

) {
    constructor() : this("",171, 90F,"2/12/1980", "1","", mutableListOf(), mutableListOf())
    override fun toString(): String {
        return super.toString()
    }
}
