package com.example.chorband.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chorband.ui.screens.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object RestablecerContrasena : Screen("restablecer_contrasena")
    object Perfil : Screen("perfil")
    object CancionesPublicas : Screen("canciones_publicas")
    object CancionesBanda : Screen("canciones_banda")
    object VerCancion : Screen("ver_cancion")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.CancionesBanda.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onOlvidasteContrasenaClick = {
                    navController.navigate(Screen.RestablecerContrasena.route)
                }
            )
        }

        composable(Screen.RestablecerContrasena.route) {
            RestablecerContrasenaScreen(
                onCancelarClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                onCancionesBandaClick = {
                    navController.navigate(Screen.CancionesBanda.route)
                },
                onCancionesClick = {
                    navController.navigate(Screen.CancionesPublicas.route)
                },
                onSalirClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.CancionesPublicas.route) {
            CancionespublicasScreen(
                viewModel = androidx.lifecycle.viewmodel.compose.viewModel<com.example.chorband.viewmodel.CancionesPublicasViewModel>(),
                onCancionClick = {
                    navController.navigate(Screen.VerCancion.route)
                },
                onPerfilClick = {
                    navController.navigate(Screen.Perfil.route)
                },
                onSalirClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.CancionesBanda.route) {
            CancionesBandaScreen(
                onCancionClick = { id ->
                    navController.navigate(Screen.VerCancion.route)
                },
                onCancionesClick = {
                    navController.navigate(Screen.CancionesPublicas.route)
                },
                onPerfilClick = {
                    navController.navigate(Screen.Perfil.route)
                },
                onSalirClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.VerCancion.route) {
            // Pantalla pendiente
        }
    }
}