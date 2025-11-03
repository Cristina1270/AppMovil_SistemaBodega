package com.example.sistemabodegamovil.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemabodegamovil.data.model.LoginReq
import com.example.sistemabodegamovil.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthUiState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val nombre: String? = null
)

class AuthViewModel : ViewModel() {
    private val _ui = MutableStateFlow(AuthUiState())
    val ui = _ui.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _ui.value = AuthUiState(loading = true)
                val res = RetrofitInstance.api.login(LoginReq(email, password))
                _ui.value = AuthUiState(success = true)
                println("Usuario autenticado: ${res.usuarioDTO}")
            } catch (e: Exception) {
                _ui.value = AuthUiState(error = "Usuario no encontrado")
            }
        }
    }
}