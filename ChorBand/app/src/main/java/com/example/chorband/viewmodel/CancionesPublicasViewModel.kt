package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CancionPublicasItem(
    val id: Int,
    val nombre: String,
    val banda: String,
    val bpm: String,
    val tono: String
)

data class CancionesPublicasUiState(
    val canciones: List<CancionPublicasItem> = emptyList(),
    val busqueda: String = "",
    val paginaActual: Int = 1
)

class CancionesPublicasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CancionesPublicasUiState())
    val uiState: StateFlow<CancionesPublicasUiState> = _uiState

    init {
        _uiState.value = _uiState.value.copy(
            canciones = List(15) {
                CancionPublicasItem(it, "Canción $it", "Banda $it", "120", "C")
            }
        )
    }

    fun onBusquedaChange(q: String) {
        _uiState.value = _uiState.value.copy(busqueda = q, paginaActual = 1)
    }

    fun onPaginaChange(p: Int) {
        _uiState.value = _uiState.value.copy(paginaActual = p)
    }

    fun paginaAnterior() {
        val p = _uiState.value.paginaActual
        if (p > 1) _uiState.value = _uiState.value.copy(paginaActual = p - 1)
    }

    fun paginaSiguiente(total: Int) {
        val p = _uiState.value.paginaActual
        if (p < total) _uiState.value = _uiState.value.copy(paginaActual = p + 1)
    }

    fun cancionesFiltradas(): List<CancionPublicasItem> {
        val q = _uiState.value.busqueda
        return if (q.isBlank()) _uiState.value.canciones
        else _uiState.value.canciones.filter {
            it.nombre.contains(q, true) || it.banda.contains(q, true)
        }
    }
}