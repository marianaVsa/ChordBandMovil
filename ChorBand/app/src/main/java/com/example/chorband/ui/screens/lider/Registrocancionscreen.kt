package com.example.chorband.ui.screens.lider

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.viewmodel.lider.RegistroCancionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroCancionScreen(
    onAgregarSeccionClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onGuardarClick: () -> Unit = {},
    onCancionesBandaClick: () -> Unit = {},
    onBandaClick: () -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    viewModel: RegistroCancionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                            text = "Líder",
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = onCancionesBandaClick,
                    icon = { Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Canciones banda", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onBandaClick,
                    icon = { Icon(Icons.Default.MusicNote, contentDescription = null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Banda", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onCancionesClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(20.dp)) },
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
                    icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Perfil", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
                        unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onSalirClick,
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(20.dp)) },
                    label = { Text("Salir", fontSize = 9.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, selectedTextColor = Color.Black,
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Registro de canción",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre de la canción
            Text("Nombre de la canción", fontSize = 13.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.nombreCancion,
                onValueChange = { viewModel.onNombreCancionChange(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del autor
            Text("Nombre del autor", fontSize = 13.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.nombreAutor,
                onValueChange = { viewModel.onNombreAutorChange(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Tono Principal
            Text("Tono Principal", fontSize = 13.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.tonoPrincipal,
                onValueChange = { viewModel.onTonoPrincipalChange(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Velocidad BPM
            Text("Velocidad(BMP)", fontSize = 13.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.velocidad,
                onValueChange = { viewModel.onVelocidadChange(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(24.dp))

            // Toggle Privada / Pública
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Privada 🔒", fontSize = 13.sp, color = Color.Black)
                Switch(
                    checked = uiState.esPublica,
                    onCheckedChange = { viewModel.onVisibilidadChange(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF2ecc9a),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Gray
                    )
                )
                Text("Publica 🌐", fontSize = 13.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // + Agregar sección
                Button(
                    onClick = onAgregarSeccionClick,
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2ecc9a),
                        contentColor = Color.White
                    )
                ) {
                    Text("+ Agregar sección", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Cancelar
                Button(
                    onClick = onCancelarClick,
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFe74c3c),
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancelar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Guardar
                Button(
                    onClick = onGuardarClick,
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2ecc9a),
                        contentColor = Color.White
                    )
                ) {
                    Text("Guardar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroCancionScreenPreview() {
    RegistroCancionScreen()
}