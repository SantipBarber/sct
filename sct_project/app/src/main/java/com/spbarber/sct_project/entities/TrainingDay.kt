package com.spbarber.sct_project.entities

data class TrainingDay(
    val numDay: Int,
    val exercise: String,
    val accessories: String,
    val trainingDone: List<Boolean>
    )
    {
    constructor() : this(0, "", "", mutableListOf())

    override fun toString(): String {
        return "Día $numDay: Ejercicio $exercise + accesorios $accessories."
    }
}
