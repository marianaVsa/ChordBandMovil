package com.example.chorband.viewmodel.lider

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

    // TODO: cargar datos del perfil desde el backend
    fun cargarPerfil() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Aquí se hará la llamada al API
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}