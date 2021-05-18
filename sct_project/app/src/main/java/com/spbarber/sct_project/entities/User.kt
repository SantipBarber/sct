package com.spbarber.sct_project.entities

import java.util.*

data class User(
    val idUser: Long,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val createAt: Date,
    val updateAt: Date
)
