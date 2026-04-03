package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CancionBandaItem(
    val id: Int,
    val nombre: String,
    val banda: String,
    val bpm: String,
    val tono: String
)

data class CancionesBandaUiState(
    val canciones: List<CancionBandaItem> = emptyList(),
    val busqueda: String = "",
    val paginaActual: Int = 1,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CancionesBandaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CancionesBandaUiState())
    val uiState: StateFlow<CancionesBandaUiState> = _uiState

    init {
        cargarCanciones()
    }

    private fun cargarCanciones() {
        // TODO: conectar con el backend
        _uiState.value = _uiState.value.copy(
            canciones = listOf(
                CancionBandaItem(1, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(2, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(3, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(4, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(5, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(6, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
                CancionBandaItem(7, "Las Mañanitas", "Banda del norte", "120", "G Sol Mayor"),
            )
        )
    }

    fun onBusquedaChange(query: String) {
        _uiState.value = _uiState.value.copy(busqueda = query, paginaActual = 1)
    }

    fun onPaginaChange(pagina: Int) {
        _uiState.value = _uiState.value.copy(paginaActual = pagina)
    }

    fun paginaAnterior() {
        val actual = _uiState.value.paginaActual
        if (actual > 1) _uiState.value = _uiState.value.copy(paginaActual = actual - 1)
    }

    fun paginaSiguiente() {
        val actual = _uiState.value.paginaActual
        _uiState.value = _uiState.value.copy(paginaActual = actual + 1)
    }

    fun cancionesFiltradas(): List<CancionBandaItem> {
        val query = _uiState.value.busqueda
        return if (query.isBlank()) _uiState.value.canciones
        else _uiState.value.canciones.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.banda.contains(query, ignoreCase = true)
        }
    }
}