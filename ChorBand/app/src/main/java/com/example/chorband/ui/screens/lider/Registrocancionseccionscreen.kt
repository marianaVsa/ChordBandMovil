package com.example.chorband.ui.screens.lider

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.viewmodel.lider.RegistroCancionSeccionViewModel

data class Seccion(
    val titulo: String,
    val lineas: List<Pair<String, String>>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroCancionSeccionScreen(
    onAgregarSeccionClick: () -> Unit = {},
    onCancionesBandaClick: () -> Unit = {},
    onBandaClick: () -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    viewModel: RegistroCancionSeccionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ChordBand", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Líder", fontSize = 12.sp, color = Color.LightGray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                NavigationBarItem(
                    selected = false, onClick = onCancionesBandaClick,
                    icon = { Icon(Icons.Default.List, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Canciones banda", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = onBandaClick,
                    icon = { Icon(Icons.Default.MusicNote, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Banda", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = onCancionesClick,
                    icon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Canciones", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = onPerfilClick,
                    icon = { Icon(Icons.Default.Person, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Perfil", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = onSalirClick,
                    icon = { Icon(Icons.Default.ExitToApp, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Salir", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text("Registro de canción", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(12.dp))

            // Tabla de grados
            TablaGrados()

            Spacer(modifier = Modifier.height(16.dp))

            // Transpositor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Transponer", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = { viewModel.subirTono() },
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp)
                ) { Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold) }

                Box(modifier = Modifier.width(32.dp), contentAlignment = Alignment.Center) {
                    Text("${uiState.semitonos}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                OutlinedButton(
                    onClick = { viewModel.bajarTono() },
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp)
                ) { Text("−", fontSize = 16.sp, fontWeight = FontWeight.Bold) }

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedButton(
                    onClick = { viewModel.resetTono() },
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(0.dp)
                ) { Text("↺", fontSize = 14.sp) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón agregar sección
            Button(
                onClick = onAgregarSeccionClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ecc9a),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("+ Agregar sección", fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Secciones
            uiState.secciones.forEach { seccion ->
                SeccionCard(seccion = seccion)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun TablaGrados() {
    val grados = listOf("I", "ii", "iii", "IV", "V", "vi", "VII°")
    val notas = listOf("C", "D", "E", "F", "G", "A", "B")
    val comodines = listOf("\$1", "\$2", "\$3", "\$4", "\$5", "\$6", "\$7")
    val labels = listOf("Gra-\ndes", "No-\ntas", "Como-\ndín")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.LightGray, RoundedCornerShape(4.dp))
    ) {
        // Fila de grados
        TablaFila(label = labels[0], valores = grados)
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
        // Fila de notas
        TablaFila(label = labels[1], valores = notas)
        HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
        // Fila de comodines
        TablaFila(label = labels[2], valores = comodines)
    }
}

@Composable
fun TablaFila(label: String, valores: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Black,
            modifier = Modifier.width(40.dp).padding(start = 4.dp),
            textAlign = TextAlign.Center
        )
        valores.forEach { valor ->
            Text(
                text = valor,
                fontSize = 11.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SeccionCard(seccion: Seccion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(seccion.titulo, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            seccion.lineas.forEach { (acordes, letra) ->
                if (acordes.isNotBlank()) {
                    Text(acordes, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                if (letra.isNotBlank()) {
                    Text(letra, fontSize = 13.sp, color = Color.Black)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroCancionSeccionScreenPreview() {
    RegistroCancionSeccionScreen()
}