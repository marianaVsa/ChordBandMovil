package com.example.chorband.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.ui.navigation.Screen
import com.example.chorband.viewmodel.CancionesPublicasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancionespublicasScreen(
    onCancionClick: (Int) -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    viewModel: CancionesPublicasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cancionesFiltradas = viewModel.cancionesFiltradas()

    val porPagina = 7
    val totalPaginas = maxOf(1, Math.ceil(cancionesFiltradas.size.toDouble() / porPagina).toInt())
    val paginaActual = uiState.paginaActual.coerceIn(1, totalPaginas)
    val cancionesPagina = cancionesFiltradas
        .drop((paginaActual - 1) * porPagina)
        .take(porPagina)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("ChordBand", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = true, onClick = {},
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("Canciones banda", fontSize = 9.sp) }
                )
                NavigationBarItem(
                    selected = false, onClick = onCancionesClick,
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Canciones", fontSize = 9.sp) }
                )
                NavigationBarItem(
                    selected = false, onClick = onPerfilClick,
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Perfil", fontSize = 9.sp) }
                )
                NavigationBarItem(
                    selected = false, onClick = onSalirClick,
                    icon = { Icon(Icons.Default.ExitToApp, null) },
                    label = { Text("Salir", fontSize = 9.sp) }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = uiState.busqueda,
                onValueChange = { viewModel.onBusquedaChange(it) },
                placeholder = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cancionesPagina) { cancion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCancionClick(cancion.id) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(cancion.nombre, fontWeight = FontWeight.Bold)
                            Text(cancion.banda)
                            Text("${cancion.bpm} - ${cancion.tono}")
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {

                OutlinedButton(
                    onClick = { viewModel.paginaAnterior() },
                    enabled = paginaActual > 1
                ) { Text("<") }

                for (i in 1..totalPaginas) {
                    OutlinedButton(onClick = { viewModel.onPaginaChange(i) }) {
                        Text("$i")
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.paginaSiguiente(totalPaginas) },
                    enabled = paginaActual < totalPaginas
                ) { Text(">") }
            }
        }
    }
}
