package com.example.chorband.ui.screens.visitante

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.viewmodel.visitante.VisitanteViewModel

data class CancionVisitante(
    val nombre: String,
    val banda: String,
    val bpm: String,
    val tono: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitanteScreen(
    onVerCancionClick: (CancionVisitante) -> Unit = {},
    onIniciarSesionClick: () -> Unit = {},
    onCrearCuentaClick: () -> Unit = {},
    viewModel: VisitanteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cancionesFiltradas = viewModel.cancionesFiltradas()

    // Paginación: 5 canciones por página
    val porPagina = 5
    val totalPaginas = maxOf(1, Math.ceil(cancionesFiltradas.size.toDouble() / porPagina).toInt())
    val paginaActual = uiState.paginaActual.coerceIn(1, totalPaginas)
    val cancionesPagina = cancionesFiltradas
        .drop((paginaActual - 1) * porPagina)
        .take(porPagina)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ChordBand",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Visitante",
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )
                    }
                },
                actions = {
                    OutlinedTextField(
                        value = uiState.busqueda,
                        onValueChange = { viewModel.onBusquedaChange(it) },
                        placeholder = {
                            Text("Buscar", color = Color.Gray, fontSize = 12.sp)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier
                            .width(160.dp)
                            .height(46.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.DarkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            focusedContainerColor = Color(0xFF2a2a2a),
                            unfocusedContainerColor = Color(0xFF2a2a2a)
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1a1a1a))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onIniciarSesionClick) {
                    Text("Iniciar sesión", color = Color.White, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onCrearCuentaClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2ecc9a),
                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Crear cuenta", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        },
        containerColor = Color(0xFFf5f5f5)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Tabla con scroll horizontal
            Box(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.padding(8.dp)) {

                    // Encabezado de tabla
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFe0e0e0))
                            .padding(vertical = 8.dp)
                    ) {
                        TablaEncabezado("Nombre de la canción", 110.dp)
                        TablaEncabezado("Nombre de la banda", 110.dp)
                        TablaEncabezado("Velocidad (BPM)", 80.dp)
                        TablaEncabezado("Tono Principal", 90.dp)
                        TablaEncabezado("Acción", 90.dp)
                    }

                    HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

                    // Estado vacío o filas
                    if (cancionesPagina.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .width(480.dp)
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay canciones públicas disponibles",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        cancionesPagina.forEach { cancion ->
                            TablaFila(
                                nombre = cancion.nombre,
                                banda = cancion.banda,
                                bpm = cancion.bpm,
                                tono = cancion.tono,
                                onVerClick = {
                                    onVerCancionClick(
                                        CancionVisitante(
                                            cancion.nombre,
                                            cancion.banda,
                                            cancion.bpm,
                                            cancion.tono
                                        )
                                    )
                                }
                            )
                            HorizontalDivider(color = Color(0xFFe0e0e0), thickness = 0.5.dp)
                        }
                    }
                }
            }

            // Paginación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Anterior
                TextButton(
                    onClick = { viewModel.paginaAnterior() },
                    enabled = paginaActual > 1
                ) {
                    Text(
                        "<< Anterior",
                        fontSize = 12.sp,
                        color = if (paginaActual > 1) Color.Black else Color.Gray
                    )
                }

                // Números de página
                for (i in 1..totalPaginas) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                if (paginaActual == i) Color.Black else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                            .border(0.5.dp, Color.Gray, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(
                            onClick = { viewModel.onPaginaChange(i) },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "$i",
                                fontSize = 12.sp,
                                color = if (paginaActual == i) Color.White else Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                // Botón Siguiente
                TextButton(
                    onClick = { viewModel.paginaSiguiente() },
                    enabled = paginaActual < totalPaginas
                ) {
                    Text(
                        "Siguiente >>",
                        fontSize = 12.sp,
                        color = if (paginaActual < totalPaginas) Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun TablaEncabezado(texto: String, ancho: Dp) {
    Text(
        text = texto,
        modifier = Modifier
            .width(ancho)
            .padding(horizontal = 6.dp),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TablaFila(
    nombre: String,
    banda: String,
    bpm: String,
    tono: String,
    onVerClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = nombre,
            modifier = Modifier.width(110.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = banda,
            modifier = Modifier.width(110.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = bpm,
            modifier = Modifier.width(80.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = tono,
            modifier = Modifier.width(90.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.width(90.dp), contentAlignment = Alignment.Center) {
            Button(
                onClick = onVerClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ecc9a),
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text("Ver canción", fontSize = 10.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisitanteScreenPreview() {
    VisitanteScreen()
}