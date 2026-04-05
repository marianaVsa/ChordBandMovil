package com.example.chorband.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chorband.SessionManager
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
        startDestination = Screen.CancionesPublicas.route
    ) {

        // Canciones publicas
        composable(Screen.CancionesPublicas.route) {
            CancionespublicasScreen(
                onCancionClick = {
                    navController.navigate(Screen.VerCancion.route)
                },
                onCancionesBandaClick = {
                    // Si ya inició sesión va directo, si no va al login
                    if (SessionManager.isLoggedIn) {
                        navController.navigate(Screen.CancionesBanda.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onPerfilClick = {
                    if (SessionManager.isLoggedIn) {
                        navController.navigate(Screen.Perfil.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onSalirClick = {
                }
            )
        }

        // Login
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    SessionManager.iniciarSesion(email = "")
                    navController.navigate(Screen.CancionesBanda.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onOlvidasteContrasenaClick = {
                    navController.navigate(Screen.RestablecerContrasena.route)
                }
            )
        }

        // Restablecer Contraseña
        composable(Screen.RestablecerContrasena.route) {
            RestablecerContrasenaScreen(
                onCancelarClick = { navController.popBackStack() }
            )
        }

        // Canciones de Banda
        composable(Screen.CancionesBanda.route) {
            CancionesBandaScreen(
                onCancionClick = { _ ->
                    navController.navigate(Screen.VerCancion.route)
                },
                onCancionesClick = {
                    navController.navigate(Screen.CancionesPublicas.route) {
                        launchSingleTop = true
                    }
                },
                onPerfilClick = {
                    navController.navigate(Screen.Perfil.route) {
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    SessionManager.cerrarSesion()
                    navController.navigate(Screen.CancionesPublicas.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Perfil
        composable(Screen.Perfil.route) {
            PerfilScreen(
                onCancionesBandaClick = {
                    navController.navigate(Screen.CancionesBanda.route) {
                        launchSingleTop = true
                    }
                },
                onCancionesClick = {
                    navController.navigate(Screen.CancionesPublicas.route) {
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    SessionManager.cerrarSesion()
                    navController.navigate(Screen.CancionesPublicas.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Ver Cancion
        composable(Screen.VerCancion.route) {
            VerCancionScreen(
                onCancionesBandaClick = {
                    if (SessionManager.isLoggedIn) {
                        navController.navigate(Screen.CancionesBanda.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onCancionesClick = {
                    navController.navigate(Screen.CancionesPublicas.route)
                },
                onPerfilClick = {
                    if (SessionManager.isLoggedIn) {
                        navController.navigate(Screen.Perfil.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                },
                onSalirClick = {
                    if (SessionManager.isLoggedIn) {
                        SessionManager.cerrarSesion()
                        navController.navigate(Screen.CancionesPublicas.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}