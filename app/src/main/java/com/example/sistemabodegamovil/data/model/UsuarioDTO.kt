package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class UsuarioDTO(
    val correo: String,
    val contrasenia: String,
    val rut: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String
)