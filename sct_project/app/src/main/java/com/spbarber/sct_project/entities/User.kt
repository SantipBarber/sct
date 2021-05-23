package com.spbarber.sct_project.entities

import java.util.*

data class User(
    //val idUser: Long,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val password: String,
    val createAt: Date,
    val updateAt: Date
) {
    constructor() : this("", "", "santiaguet@me.com", "123456", Date(), Date())
}
