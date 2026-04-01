package com.example.chorband.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
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
import com.example.chorband.viewmodel.Cancion
import com.example.chorband.viewmodel.CancionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancionesBandaScreen(
    onVerCancionClick: (Cancion) -> Unit = {},
    onCancionesClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    cancionesViewModel: CancionesViewModel = viewModel()
) {
    val uiState by cancionesViewModel.uiState.collectAsState()
    val cancionesFiltradas = cancionesViewModel.cancionesFiltradas()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ChordBand",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    // Buscador en el topbar
                    OutlinedTextField(
                        value = uiState.busqueda,
                        onValueChange = { cancionesViewModel.onBusquedaChange(it) },
                        placeholder = {
                            Text(
                                text = "Buscar",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(48.dp)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = 0,
                onCancionesBandaClick = { },
                onCancionesClick = onCancionesClick,
                onPerfilClick = onPerfilClick,
                onSalirClick = onSalirClick
            )
        },
        containerColor = Color(0xFFf5f5f5)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(cancionesFiltradas) { cancion ->
                CancionCard(
                    cancion = cancion,
                    onVerCancionClick = { onVerCancionClick(cancion) }
                )
            }
        }
    }
}

@Composable
fun CancionCard(
    cancion: Cancion,
    onVerCancionClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2a2a2a))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cancion.nombre,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = cancion.banda,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }

            Button(
                onClick = onVerCancionClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF444444),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Ver canción",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onCancionesBandaClick: () -> Unit,
    onCancionesClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onSalirClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onCancionesBandaClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.LibraryMusic,
                    contentDescription = "Canciones banda"
                )
            },
            label = {
                Text(
                    text = "Canciones banda",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onCancionesClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Canciones"
                )
            },
            label = {
                Text(
                    text = "Canciones",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onPerfilClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text(
                    text = "Perfil",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = onSalirClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Salir"
                )
            },
            label = {
                Text(
                    text = "Salir",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CancionesBandaScreenPreview() {
    CancionesBandaScreen()
}