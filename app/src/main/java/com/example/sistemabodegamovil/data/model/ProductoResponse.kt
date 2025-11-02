package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class ProductoResponse(
    val estado: String,
    val mensaje: String,
    val producto: Producto
)
