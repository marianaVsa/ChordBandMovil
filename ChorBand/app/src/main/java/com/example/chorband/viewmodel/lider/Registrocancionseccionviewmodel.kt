package com.example.chorband.viewmodel.lider

import androidx.lifecycle.ViewModel
import com.example.chorband.ui.screens.lider.Seccion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RegistroCancionSeccionUiState(
    val semitonos: Int = 0,
    val secciones: List<Seccion> = listOf(
        Seccion(
            titulo = "Intro",
            lineas = listOf(Pair("G - D - G", ""))
        ),
        Seccion(
            titulo = "Verso 1",
            lineas = listOf(
                Pair("G          D", "Estas son las mañanitas"),
                Pair("G            C", "Que cantaba el rey David"),
                Pair("G", "Hoy por ser día de tu santo"),
                Pair("C  D  G", "Te las cantamos a ti")
            )
        )
    )
)

class RegistroCancionSeccionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroCancionSeccionUiState())
    val uiState: StateFlow<RegistroCancionSeccionUiState> = _uiState

    private val escala = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

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

    fun agregarSeccion(seccion: Seccion) {
        val lista = _uiState.value.secciones.toMutableList()
        lista.add(seccion)
        _uiState.value = _uiState.value.copy(secciones = lista)
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
}