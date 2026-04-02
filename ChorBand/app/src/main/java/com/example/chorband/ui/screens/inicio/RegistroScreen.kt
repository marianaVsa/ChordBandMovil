package com.example.chorband.ui.screens.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.R
import com.example.chorband.viewmodel.inicio.RegistroViewModel

@Composable
fun RegistroScreen(
    onRegistroSuccess: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    registroViewModel: RegistroViewModel = viewModel()
) {
    val uiState by registroViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registroSuccess) {
        if (uiState.registroSuccess) {
            onRegistroSuccess()
            registroViewModel.resetRegistroSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // Logo circular
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_chordband),
                contentDescription = "ChordBand Logo",
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo Nombre
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { registroViewModel.onNombreChange(it) },
            placeholder = { Text("Ingrese Nombre", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Apellidos
        OutlinedTextField(
            value = uiState.apellidos,
            onValueChange = { registroViewModel.onApellidosChange(it) },
            placeholder = { Text("Ingrese Apellidos", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Nombre de usuario
        OutlinedTextField(
            value = uiState.nombreUsuario,
            onValueChange = { registroViewModel.onNombreUsuarioChange(it) },
            placeholder = { Text("Ingrese Nombre de usuario", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Teléfono
        OutlinedTextField(
            value = uiState.telefono,
            onValueChange = { registroViewModel.onTelefonoChange(it) },
            placeholder = { Text("Ingrese Numero de teléfono", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Correo
        OutlinedTextField(
            value = uiState.correo,
            onValueChange = { registroViewModel.onCorreoChange(it) },
            placeholder = { Text("Ingrese Correo electrónico", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = uiState.contrasena,
            onValueChange = { registroViewModel.onContrasenaChange(it) },
            placeholder = { Text("Ingrese Contraseña", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = campoColores()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo Nombre de la banda
        OutlinedTextField(
            value = uiState.nombreBanda,
            onValueChange = { registroViewModel.onNombreBandaChange(it) },
            placeholder = { Text("Ingrese Nombre de la banda", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = campoColores()
        )

        // Mensaje de error
        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.errorMessage!!,
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Registrar
        Button(
            onClick = { registroViewModel.onRegistrarClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            enabled = !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2ecc9a),
                contentColor = Color.White
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text(
                    text = "Registrar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Cancelar
        Button(
            onClick = onCancelarClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFe74c3c),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Cancelar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color.Black,
    unfocusedBorderColor = Color.LightGray,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    cursorColor = Color.Black
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen()
}