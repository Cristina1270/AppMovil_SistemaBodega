package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class Producto(
    val id: String? = null,
    val codigo: String,
    val nombre: String,
    val precio: Double? = null
)
