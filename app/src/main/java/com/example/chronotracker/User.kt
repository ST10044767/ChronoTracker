package com.example.chronotracker

import java.io.Serializable

data class User(
    val userId: String = "", // Default values to empty strings
    val email: String = "",
    val password: String = ""
) {

    constructor() : this("", "", "")
}
