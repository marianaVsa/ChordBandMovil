package com.example.chorband

object SessionManager {
    var isLoggedIn: Boolean = false
    var usuarioEmail: String = ""
    var usuarioNombre: String = ""

    fun iniciarSesion(email: String) {
        isLoggedIn = true
        usuarioEmail = email
    }

    fun cerrarSesion() {
        isLoggedIn = false
        usuarioEmail = ""
        usuarioNombre = ""
    }
}