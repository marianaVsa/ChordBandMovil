package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class PerfilUiState(
    val nombreCompleto: String = "Nombre completo del musico",
    val correo: String = "Correo del musico",
    val telefono: String = "Numero de telefono del musico",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState

    fun cargarPerfil(nombreCompleto: String, correo: String, telefono: String) {
        _uiState.value = _uiState.value.copy(
            nombreCompleto = nombreCompleto,
            correo = correo,
            telefono = telefono
        )
    }

    // TODO: cargar datos del perfil desde el backend
    fun cargarPerfilDesdeBackend(usuarioId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Aquí se hará la llamada al API
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}