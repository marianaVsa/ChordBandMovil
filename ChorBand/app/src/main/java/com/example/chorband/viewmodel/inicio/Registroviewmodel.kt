package com.example.chorband.viewmodel.inicio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RegistroUiState(
    val nombre: String = "",
    val apellidos: String = "",
    val nombreUsuario: String = "",
    val telefono: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val nombreBanda: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registroSuccess: Boolean = false
)

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState

    fun onNombreChange(value: String) { _uiState.value = _uiState.value.copy(nombre = value) }
    fun onApellidosChange(value: String) { _uiState.value = _uiState.value.copy(apellidos = value) }
    fun onNombreUsuarioChange(value: String) { _uiState.value = _uiState.value.copy(nombreUsuario = value) }
    fun onTelefonoChange(value: String) { _uiState.value = _uiState.value.copy(telefono = value) }
    fun onCorreoChange(value: String) { _uiState.value = _uiState.value.copy(correo = value) }
    fun onContrasenaChange(value: String) { _uiState.value = _uiState.value.copy(contrasena = value) }
    fun onNombreBandaChange(value: String) { _uiState.value = _uiState.value.copy(nombreBanda = value) }

    fun onRegistrarClick() {
        val state = _uiState.value

        if (state.nombre.isBlank() || state.apellidos.isBlank() ||
            state.nombreUsuario.isBlank() || state.telefono.isBlank() ||
            state.correo.isBlank() || state.contrasena.isBlank() ||
            state.nombreBanda.isBlank()
        ) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor completa todos los campos")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        // TODO: conectar con el backend (Spring Boot)
        _uiState.value = _uiState.value.copy(isLoading = false, registroSuccess = true)
    }

    fun resetRegistroSuccess() {
        _uiState.value = _uiState.value.copy(registroSuccess = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}