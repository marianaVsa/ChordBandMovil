package com.example.chorband.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    onCancionesBandaClick: () -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    viewModel: PerfilViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
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
                    selected = true, onClick = {},
                    icon = { Icon(Icons.Default.Person, null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Perfil", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
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
        containerColor = Color.Black
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val rosaLeft = w * 0.70f
                val rosaRight = w * 0.86f
                val lineaW = w * 0.018f

                // 1. FONDO VERDE TEAL
                drawRect(color = Color(0xFF16A085))

                // 2. FRANJA ROSA vertical
                drawRect(
                    color = Color(0xFFE8D1C5),
                    topLeft = Offset(rosaLeft, 0f),
                    size = Size(rosaRight - rosaLeft, h)
                )

                // 3. LÍNEA BLANCA vertical izquierda de la rosa
                drawRect(
                    color = Color.White,
                    topLeft = Offset(rosaLeft - lineaW, 0f),
                    size = Size(lineaW, h)
                )

                // 4. NEGRO PRINCIPAL
                val pathNegro = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(rosaLeft - lineaW, 0f)
                    lineTo(rosaLeft - lineaW, h * 0.62f)
                    lineTo(w * 0.15f, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(pathNegro, color = Color.Black)


                // 5. TRIÁNGULO VERDE — esquina superior izquierda
                val pathTriSup = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w * 0.20f, 0f)
                    lineTo(0f, h * 0.16f)
                    close()
                }
                drawPath(pathTriSup, color = Color(0xFF16A085))

                // 6. LÍNEA BLANCA DIAGONAL SUPERIOR
                val pathLinSup = Path().apply {
                    moveTo(0f, h * 0.16f)
                    lineTo(w * 0.016f, h * 0.16f)
                    lineTo(w * 0.216f, 0f)
                    lineTo(w * 0.20f, 0f)
                    close()
                }
                drawPath(pathLinSup, color = Color.White)

                // 7. TRIÁNGULO VERDE — esquina inferior derecha
                val pathTriInf = Path().apply {
                    moveTo(w, h)
                    lineTo(w * 0.85f, h)
                    lineTo(w, h * 0.84f)
                    close()
                }
                drawPath(pathTriInf, color = Color(0xFF16A085))

                // 8. LÍNEA BLANCA DIAGONAL INFERIOR
                val pathLinInf = Path().apply {
                    moveTo(rosaLeft - lineaW, h * 0.60f)
                    lineTo(rosaLeft - lineaW, h * 0.635f)
                    lineTo(w * 0.17f, h)
                    lineTo(w * 0.14f, h)
                    close()
                }
                drawPath(pathLinInf, color = Color.White)
            }

            // ICONO
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "𝄞",
                    fontSize = 55.sp,
                    color = Color(0xFF555555),
                    modifier = Modifier.offset(x = 105.dp, y = 10.dp)
                )
            }

            // DATOS DEL MÚSICO
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.62f)
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .offset(y = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = uiState.nombreCompleto,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = uiState.correo,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = uiState.telefono,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}