package com.example.sistemabodegamovil.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.sistemabodegamovil.data.model.*


interface ApiService {

    @GET("producto")
    suspend fun getProductos(): ProductosResponse

    @GET("producto/{codigo}")
    suspend fun getProducto(@Path("codigo") codigo: String): ProductoResponse

    @POST("producto")
    suspend fun crearProducto(@Body p: Producto): ProductoResponse

    @POST("usuario")
    suspend fun crearUsuario(@Body dto: UsuarioDTO): UsuarioResponse

    @POST("usuario/login")
    suspend fun login(@Body body: LoginReq): UsuarioResponse
}
