package com.example.shareroutine.domain.model

import java.io.Serializable

data class User(
    val id: String,
    val email: String,
    var nickname: String
) : Serializable
