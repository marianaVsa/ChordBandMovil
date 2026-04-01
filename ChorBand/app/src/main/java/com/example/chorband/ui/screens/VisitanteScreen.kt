package com.example.chorband.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onCrearCuentaClick: () -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var paginaActual by remember { mutableStateOf(1) }
    val totalPaginas = 3

    val canciones = listOf(
        CancionVisitante("Las Mañanitas", "Banda del norte", "12", "G Sol Mayor"),
        CancionVisitante("Entre Dos Tierras", "Héroes del Silencio", "12", "C Do mayor"),
        CancionVisitante("De Música Ligera", "Soda Stereo", "12", "C Do mayor"),
        CancionVisitante("Entre Dos Tierras", "Héroes del Silencio", "12", "C Do mayor"),
        CancionVisitante("Las Mañanitas", "Entre Dos Tierras", "12", "C Do mayor"),
    )

    val cancionesFiltradas = if (busqueda.isBlank()) canciones
    else canciones.filter {
        it.nombre.contains(busqueda, ignoreCase = true) ||
                it.banda.contains(busqueda, ignoreCase = true)
    }

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
                        value = busqueda,
                        onValueChange = { busqueda = it },
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
            // Footer con Iniciar sesión y Crear cuenta
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1a1a1a))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onIniciarSesionClick) {
                    Text(
                        text = "Iniciar sesión",
                        color = Color.White,
                        fontSize = 13.sp
                    )
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
                    Text(text = "Crear cuenta", fontSize = 13.sp, fontWeight = FontWeight.Medium)
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
                            .fillMaxWidth()
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

                    // Filas
                    cancionesFiltradas.forEach { cancion ->
                        TablaFila(
                            cancion = cancion,
                            onVerClick = { onVerCancionClick(cancion) }
                        )
                        HorizontalDivider(color = Color(0xFFe0e0e0), thickness = 0.5.dp)
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
                TextButton(
                    onClick = { if (paginaActual > 1) paginaActual-- },
                    enabled = paginaActual > 1
                ) {
                    Text("<< Anterior", fontSize = 12.sp, color = if (paginaActual > 1) Color.Black else Color.Gray)
                }

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
                            onClick = { paginaActual = i },
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

                TextButton(
                    onClick = { if (paginaActual < totalPaginas) paginaActual++ },
                    enabled = paginaActual < totalPaginas
                ) {
                    Text("Siguiente >>", fontSize = 12.sp, color = if (paginaActual < totalPaginas) Color.Black else Color.Gray)
                }
            }
        }
    }
}

@Composable
fun TablaEncabezado(texto: String, ancho: androidx.compose.ui.unit.Dp) {
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
    cancion: CancionVisitante,
    onVerClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = cancion.nombre,
            modifier = Modifier.width(110.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = cancion.banda,
            modifier = Modifier.width(110.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = cancion.bpm,
            modifier = Modifier.width(80.dp).padding(horizontal = 6.dp),
            fontSize = 11.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = cancion.tono,
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
                Text(text = "Ver", fontSize = 10.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisitanteScreenPreview() {
    VisitanteScreen()
}