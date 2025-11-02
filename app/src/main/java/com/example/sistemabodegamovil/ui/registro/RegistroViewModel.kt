package com.example.sistemabodegamovil.ui.registro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemabodegamovil.data.model.UsuarioDTO
import com.example.sistemabodegamovil.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {
    var ui by mutableStateOf<UiState>(UiState())
        private set

    data class UiState(
        val nombre: String = "",
        val apellidoPaterno: String = "",
        val apellidoMaterno: String = "",
        val rut: String = "",
        val correo: String = "",
        val pass: String = "",
        val loading: Boolean = false,
        val error: String? = null,
        val ok: Boolean = false
    )

    fun onChange(field: (UiState) -> String, value: String) {
        ui = when (field) {
            UiState::nombre -> ui.copy(nombre = value)
            UiState::apellidoPaterno -> ui.copy(apellidoPaterno = value)
            UiState::apellidoMaterno -> ui.copy(apellidoMaterno = value)
            UiState::rut -> ui.copy(rut = value)
            UiState::correo -> ui.copy(correo = value)
            UiState::pass -> ui.copy(pass = value)
            else -> ui
        }
    }

    fun registrar() {
        viewModelScope.launch {
            ui = ui.copy(loading = true, error = null)
            try {
                val dto = UsuarioDTO(
                    correo = ui.correo,
                    contrasenia = ui.pass,
                    rut = ui.rut,
                    nombre = ui.nombre,
                    apellidoPaterno = ui.apellidoPaterno,
                    apellidoMaterno = ui.apellidoMaterno
                )
                val resp = RetrofitInstance.api.crearUsuario(dto)
                ui = if (resp.estado == "OK") ui.copy(loading = false, ok = true)
                else ui.copy(loading = false, error = resp.mensaje)
            } catch (e: Exception) {
                ui = ui.copy(loading = false, error = e.message ?: "Error de red")
            }
        }
    }
}