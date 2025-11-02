package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class UsuarioResponse(
    val estado: String,
    val mensaje: String,
    val usuarioDTO: UsuarioDTO
)
