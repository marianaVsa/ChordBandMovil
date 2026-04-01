package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CancionVisitanteItem(
    val id: Int,
    val nombre: String,
    val banda: String,
    val bpm: String,
    val tono: String
)

data class VisitanteUiState(
    val canciones: List<CancionVisitanteItem> = emptyList(),
    val busqueda: String = "",
    val paginaActual: Int = 1,
    val totalPaginas: Int = 3,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class VisitanteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VisitanteUiState())
    val uiState: StateFlow<VisitanteUiState> = _uiState

    init {
        cargarCanciones()
    }

    private fun cargarCanciones() {
        // TODO: conectar con el backend (Spring Boot)
        // Por ahora datos de prueba
        _uiState.value = _uiState.value.copy(
            canciones = listOf(
                CancionVisitanteItem(1, "Las Mañanitas", "Banda del norte", "12", "G Sol Mayor"),
                CancionVisitanteItem(2, "Entre Dos Tierras", "Héroes del Silencio", "12", "C Do mayor"),
                CancionVisitanteItem(3, "De Música Ligera", "Soda Stereo", "12", "C Do mayor"),
                CancionVisitanteItem(4, "Entre Dos Tierras", "Héroes del Silencio", "12", "C Do mayor"),
                CancionVisitanteItem(5, "Las Mañanitas", "Entre Dos Tierras", "12", "C Do mayor"),
            )
        )
    }

    fun onBusquedaChange(query: String) {
        _uiState.value = _uiState.value.copy(busqueda = query)
    }

    fun onPaginaChange(pagina: Int) {
        _uiState.value = _uiState.value.copy(paginaActual = pagina)
        // TODO: cargar canciones de la página seleccionada desde el backend
    }

    fun paginaAnterior() {
        val actual = _uiState.value.paginaActual
        if (actual > 1) {
            _uiState.value = _uiState.value.copy(paginaActual = actual - 1)
        }
    }

    fun paginaSiguiente() {
        val actual = _uiState.value.paginaActual
        val total = _uiState.value.totalPaginas
        if (actual < total) {
            _uiState.value = _uiState.value.copy(paginaActual = actual + 1)
        }
    }

    fun cancionesFiltradas(): List<CancionVisitanteItem> {
        val query = _uiState.value.busqueda
        return if (query.isBlank()) {
            _uiState.value.canciones
        } else {
            _uiState.value.canciones.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.banda.contains(query, ignoreCase = true)
            }
        }
    }
}