package com.example.chorband.ui.screens.visitante

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.chorband.viewmodel.visitante.VisitanteVerCancionViewModel

data class SeccionCancion(
    val titulo: String,
    val lineas: List<Pair<String, String>> // (acordes, letra)
)

@Composable
fun VisitanteVerCancionScreen(
    nombreCancion: String = "LAS MAÑANITAS",
    onIniciarSesionClick: () -> Unit = {},
    onCrearCuentaClick: () -> Unit = {},
    viewModel: VisitanteVerCancionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Secciones originales (sin transponer)
    val seccionesOriginales = listOf(
        SeccionCancion(
            titulo = "Intro",
            lineas = listOf(Pair("G - D - G", ""))
        ),
        SeccionCancion(
            titulo = "Verso 1",
            lineas = listOf(
                Pair("G          D", "Estas son las mañanitas"),
                Pair("G            C", "Que cantaba el rey David"),
                Pair("G", "Hoy por ser día de tu santo"),
                Pair("C  D  G", "Te las cantamos a ti")
            )
        ),
        SeccionCancion(
            titulo = "Verso 2",
            lineas = listOf(
                Pair("D          G", "Despierta, mi bien, despierta"),
                Pair("D          G", "Mira que ya amaneció"),
                Pair("C       G", "Ya los pajarillos cantan"),
                Pair("C  D  G", "La luna ya se metió")
            )
        ),
        SeccionCancion(
            titulo = "Verso 3",
            lineas = listOf(
                Pair("G", "Qué linda está la mañana"),
                Pair("D", "En que vengo a saludarte"),
                Pair("D", "")
            )
        )
    )

    Scaffold(
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
                .verticalScroll(rememberScrollState())
        ) {
            // Barra de transposición
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transponer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Botón +
                    OutlinedButton(
                        onClick = { viewModel.subirTono() },
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    // Contador de semitonos
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${uiState.semitonos}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    // Botón -
                    OutlinedButton(
                        onClick = { viewModel.bajarTono() },
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("−", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón reset
                    OutlinedButton(
                        onClick = { viewModel.resetTono() },
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                    ) {
                        Text("↺", fontSize = 16.sp)
                    }
                }
            }

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

            // Secciones con acordes transpuestos en tiempo real
            seccionesOriginales.forEach { seccion ->
                SeccionCancionCard(
                    seccion = seccion,
                    onTransponerAcorde = { acorde -> viewModel.transponerAcorde(acorde) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SeccionCancionCard(
    seccion: SeccionCancion,
    onTransponerAcorde: (String) -> String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título de la sección
            Text(
                text = seccion.titulo,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Líneas con acordes transpuestos
            seccion.lineas.forEach { (acordes, letra) ->
                if (acordes.isNotBlank()) {
                    // Transponer los acordes en tiempo real
                    Text(
                        text = onTransponerAcorde(acordes),
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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VisitanteVerCancionScreenPreview() {
    VisitanteVerCancionScreen()
}