package com.spbarber.sct_project.entities

data class TrainingDay(
    val numDay: Int,
    val exercise: String,
    val accessories: String
    )
    {
    constructor() : this(0, "", "")

    override fun toString(): String {
        return "Día $numDay: Ejercicio $exercise + accesorios $accessories."
    }
}
