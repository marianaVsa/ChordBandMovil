package com.example.chorband.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chorband.ui.screens.LoginScreen
import com.example.chorband.ui.screens.VisitanteScreen
import com.example.chorband.ui.screens.VisitanteVerCancionScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Visitante : Screen("visitante")
    object VisitanteVerCancion : Screen("visitante_ver_cancion")
    // Se irán agregando las demás pantallas:
    // object CancionesBanda : Screen("canciones_banda")
    // object CancionDetalle : Screen("cancion_detalle")
    // object Perfil : Screen("perfil")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Aquí podrías validar si quieres
                    navController.navigate(Screen.Visitante.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Visitante.route) {
            VisitanteScreen(
                onIniciarSesionClick = {
                    navController.navigate(Screen.Login.route)
                },
                onCrearCuentaClick = {
                    // TODO: navegar a pantalla de registro
                },
                onVerCancionClick = { _ ->
                    navController.navigate(Screen.VisitanteVerCancion.route)
                }
            )
        }

        composable(Screen.VisitanteVerCancion.route) {
            VisitanteVerCancionScreen(
                onIniciarSesionClick = {
                    navController.navigate(Screen.Login.route)
                },
                onCrearCuentaClick = {
                    // TODO: navegar a pantalla de registro
                }
            )
        }

        // Se irán agregando las demás rutas aquí
    }
}