package com.example.chorband.viewmodel.lider

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class BandaIntegrantesUiState(
    val nombreBanda: String = "Nombre de la banda",
    val integrantes: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class BandaIntegrantesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BandaIntegrantesUiState())
    val uiState: StateFlow<BandaIntegrantesUiState> = _uiState

    fun agregarIntegrante(nombre: String) {
        val lista = _uiState.value.integrantes.toMutableList()
        lista.add(nombre)
        _uiState.value = _uiState.value.copy(integrantes = lista)
    }

    fun eliminarIntegrante(nombre: String) {
        val lista = _uiState.value.integrantes.toMutableList()
        lista.remove(nombre)
        _uiState.value = _uiState.value.copy(integrantes = lista)
    }

    // TODO: cargar banda e integrantes desde el backend
    fun cargarBanda(bandaId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}