package com.example.chorband.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
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
import com.example.chorband.viewmodel.VerCancionViewModel

data class SeccionVer(
    val titulo: String,
    val lineas: List<Pair<String, String>> // (acordes, letra)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerCancionScreen(
    nombreCancion: String = "LAS MAÑANITAS",
    onCancionesBandaClick: () -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    viewModel: VerCancionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val seccionesOriginales = listOf(
        SeccionVer(
            titulo = "Intro",
            lineas = listOf(Pair("G - D - G", ""))
        ),
        SeccionVer(
            titulo = "Verso 1",
            lineas = listOf(
                Pair("G          D", "Estas son las mañanitas"),
                Pair("G            C", "Que cantaba el rey David"),
                Pair("G", "Hoy por ser día de tu santo"),
                Pair("C  D  G", "Te las cantamos a ti")
            )
        ),
        SeccionVer(
            titulo = "Verso 2",
            lineas = listOf(
                Pair("D          G", "Despierta, mi bien, despierta"),
                Pair("D          G", "Mira que ya amaneció"),
                Pair("C       G", "Ya los pajarillos cantan"),
                Pair("C  D  G", "La luna ya se metió")
            )
        ),
        SeccionVer(
            titulo = "Verso 3",
            lineas = listOf(
                Pair("G", "Qué linda está la mañana"),
                Pair("D", "En que vengo a saludarte"),
                Pair("G", "Venimos todos con gusto"),
                Pair("G", "Y placer a felicitarte")
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ChordBand",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                .verticalScroll(rememberScrollState())
        ) {
            // Barra de transposición
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transponer",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.subirTono() },
                        modifier = Modifier.size(34.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Box(
                        modifier = Modifier.width(34.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${uiState.semitonos}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.bajarTono() },
                        modifier = Modifier.size(34.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("−", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    OutlinedButton(
                        onClick = { viewModel.resetTono() },
                        modifier = Modifier.size(34.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("↺", fontSize = 14.sp)
                    }
                }
            }

            HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la canción
            Text(
                text = nombreCancion,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Secciones con acordes transpuestos
            seccionesOriginales.forEach { seccion ->
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    // Título de sección entre corchetes
                    Text(
                        text = "[${seccion.titulo}]",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    seccion.lineas.forEach { (acordes, letra) ->
                        if (acordes.isNotBlank()) {
                            Text(
                                text = viewModel.transponerAcorde(acordes),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        if (letra.isNotBlank()) {
                            Text(
                                text = letra,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerCancionScreenPreview() {
    VerCancionScreen()
}