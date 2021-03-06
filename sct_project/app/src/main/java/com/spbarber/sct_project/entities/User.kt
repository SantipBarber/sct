package com.spbarber.sct_project.entities

import java.util.*

data class User(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val password: String,
    val createAt: Date,
    val updateAt: Date
) {
    constructor() : this("", "", "santiaguet@me.com", "123456", Date(), Date())

    override fun toString(): String {
        return super.toString()
    }
}
