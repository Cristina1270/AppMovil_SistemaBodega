package com.example.sistemabodegamovil.data.model

@kotlinx.serialization.Serializable
data class ProductosResponse(
    val estado: String,
    val mensaje: String,
    val productos: List<Producto> = emptyList()
)
