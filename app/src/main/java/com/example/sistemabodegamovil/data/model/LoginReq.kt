package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class LoginReq(
    val correo: String,
    val contrasenia: String
)
