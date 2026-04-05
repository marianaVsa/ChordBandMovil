package com.example.chorband.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class VerCancionUiState(
    val semitonos: Int = 0,
    val nombreCancion: String = "LAS MAÑANITAS",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class VerCancionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VerCancionUiState())
    val uiState: StateFlow<VerCancionUiState> = _uiState

    private val escala = listOf(
        "C", "C#", "D", "D#", "E", "F",
        "F#", "G", "G#", "A", "A#", "B"
    )

    fun subirTono() {
        val actual = _uiState.value.semitonos
        if (actual < 12) _uiState.value = _uiState.value.copy(semitonos = actual + 1)
    }

    fun bajarTono() {
        val actual = _uiState.value.semitonos
        if (actual > -12) _uiState.value = _uiState.value.copy(semitonos = actual - 1)
    }

    fun resetTono() {
        _uiState.value = _uiState.value.copy(semitonos = 0)
    }

    fun transponerAcorde(acorde: String): String {
        val semitonos = _uiState.value.semitonos
        if (semitonos == 0) return acorde

        return acorde.split(" ").joinToString(" ") { parte ->
            val nota = escala.firstOrNull { parte.startsWith(it) }
            if (nota != null) {
                val indice = escala.indexOf(nota)
                val nuevoIndice = ((indice + semitonos) % 12 + 12) % 12
                parte.replaceFirst(nota, escala[nuevoIndice])
            } else parte
        }
    }

    fun cargarCancion(id: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Aquí se hará la llamada al API
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
}