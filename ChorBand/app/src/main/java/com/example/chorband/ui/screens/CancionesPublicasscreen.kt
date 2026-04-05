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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.viewmodel.CancionesPublicasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancionespublicasScreen(
    onCancionClick: (Int) -> Unit = {},
    onCancionesBandaClick: () -> Unit = {},
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
            NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = onCancionesBandaClick,
                    icon = { Icon(Icons.Default.List, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Canciones banda", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Canciones", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onPerfilClick,
                    icon = { Icon(Icons.Default.Person, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Perfil", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onSalirClick,
                    icon = { Icon(Icons.Default.ExitToApp, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Salir", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
        containerColor = Color(0xFFf5f5f5)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.busqueda,
                onValueChange = { viewModel.onBusquedaChange(it) },
                placeholder = { Text("Buscar por canción o banda", color = Color.Gray, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cancionesPagina) { cancion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCancionClick(cancion.id) },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(cancion.nombre, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(cancion.banda, fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${cancion.bpm}    ${cancion.tono}", fontSize = 12.sp, color = Color.Gray)
                            }
                            Icon(Icons.Default.ChevronRight, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { viewModel.paginaAnterior() },
                    enabled = paginaActual > 1,
                    modifier = Modifier.size(36.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("<", fontSize = 14.sp, color = if (paginaActual > 1) Color.Black else Color.Gray)
                }

                Spacer(modifier = Modifier.width(4.dp))

                for (i in 1..totalPaginas) {
                    OutlinedButton(
                        onClick = { viewModel.onPaginaChange(i) },
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (paginaActual == i) Color.Black else Color.Transparent
                        )
                    ) {
                        Text("$i", fontSize = 13.sp, color = if (paginaActual == i) Color.White else Color.Black)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                OutlinedButton(
                    onClick = { viewModel.paginaSiguiente(totalPaginas) },
                    enabled = paginaActual < totalPaginas,
                    modifier = Modifier.size(36.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(">", fontSize = 14.sp, color = if (paginaActual < totalPaginas) Color.Black else Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}