package com.example.chorband.viewmodel.lider

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RegistroCancionUiState(
    val nombreCancion: String = "",
    val nombreAutor: String = "",
    val tonoPrincipal: String = "",
    val velocidad: String = "",
    val esPublica: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val guardadoSuccess: Boolean = false
)

class RegistroCancionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroCancionUiState())
    val uiState: StateFlow<RegistroCancionUiState> = _uiState

    fun onNombreCancionChange(value: String) { _uiState.value = _uiState.value.copy(nombreCancion = value) }
    fun onNombreAutorChange(value: String) { _uiState.value = _uiState.value.copy(nombreAutor = value) }
    fun onTonoPrincipalChange(value: String) { _uiState.value = _uiState.value.copy(tonoPrincipal = value) }
    fun onVelocidadChange(value: String) { _uiState.value = _uiState.value.copy(velocidad = value) }
    fun onVisibilidadChange(value: Boolean) { _uiState.value = _uiState.value.copy(esPublica = value) }

    fun onGuardarClick() {
        val state = _uiState.value
        if (state.nombreCancion.isBlank() || state.nombreAutor.isBlank() ||
            state.tonoPrincipal.isBlank() || state.velocidad.isBlank()
        ) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor completa todos los campos")
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        // TODO: conectar con el backend
        _uiState.value = _uiState.value.copy(isLoading = false, guardadoSuccess = true)
    }

    fun resetGuardadoSuccess() {
        _uiState.value = _uiState.value.copy(guardadoSuccess = false)
    }
}