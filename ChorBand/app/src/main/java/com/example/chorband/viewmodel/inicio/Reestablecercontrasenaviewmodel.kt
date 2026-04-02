package com.example.chorband.viewmodel.inicio

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ReestablecerContrasenaUiState(
    val correo: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val envioSuccess: Boolean = false
)

class ReestablecerContrasenaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReestablecerContrasenaUiState())
    val uiState: StateFlow<ReestablecerContrasenaUiState> = _uiState

    fun onCorreoChange(value: String) {
        _uiState.value = _uiState.value.copy(correo = value)
    }

    fun onEnviarClick() {
        val correo = _uiState.value.correo

        if (correo.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingresa tu correo")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Ingresa un correo válido")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        // TODO: conectar con el backend (Spring Boot)
        _uiState.value = _uiState.value.copy(isLoading = false, envioSuccess = true)
    }

    fun resetEnvioSuccess() {
        _uiState.value = _uiState.value.copy(envioSuccess = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}