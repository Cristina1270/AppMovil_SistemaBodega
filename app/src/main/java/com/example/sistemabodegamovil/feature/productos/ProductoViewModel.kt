package com.example.sistemabodegamovil.feature.productos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemabodegamovil.data.local.AppDatabase
import com.example.sistemabodegamovil.data.local.ProductoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).productoDao()
    val productos = dao.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun agregarProducto(codigo: String, nombre: String, precio: Double?) {
        viewModelScope.launch {
            dao.insert(ProductoEntity(codigo = codigo, nombre = nombre, precio = precio))
        }
    }
}