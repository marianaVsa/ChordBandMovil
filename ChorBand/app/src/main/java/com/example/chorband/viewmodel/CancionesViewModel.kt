package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Cancion(
    val id: Int,
    val nombre: String,
    val banda: String
)

data class CancionesUiState(
    val canciones: List<Cancion> = emptyList(),
    val busqueda: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CancionesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CancionesUiState())
    val uiState: StateFlow<CancionesUiState> = _uiState

    init {
        cargarCanciones()
    }

    private fun cargarCanciones() {
        // TODO: conectar con el backend (Spring Boot)
        // Por ahora datos de prueba
        _uiState.value = _uiState.value.copy(
            canciones = listOf(
                Cancion(1, "Nombre de la canción", "Nombre de la banda"),
                Cancion(2, "Nombre de la canción", "Nombre de la banda"),
                Cancion(3, "Nombre de la canción", "Nombre de la banda"),
                Cancion(4, "Nombre de la canción", "Nombre de la banda"),
                Cancion(5, "Nombre de la canción", "Nombre de la banda"),
                Cancion(6, "Nombre de la canción", "Nombre de la banda"),
                Cancion(7, "Nombre de la canción", "Nombre de la banda"),
            )
        )
    }

    fun onBusquedaChange(query: String) {
        _uiState.value = _uiState.value.copy(busqueda = query)
    }

    fun cancionesFiltradas(): List<Cancion> {
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