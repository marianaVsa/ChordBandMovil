package com.example.chorband.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chorband.R
import com.example.chorband.viewmodel.RestablecerContrasenaViewModel

@Composable
fun RestablecerContrasenaScreen(
    onEnviarSuccess: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    viewModel: RestablecerContrasenaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.envioSuccess) {
        if (uiState.envioSuccess) {
            onEnviarSuccess()
            viewModel.resetEnvioSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo circular
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_chordband),
                contentDescription = "ChordBand Logo",
                modifier = Modifier.size(85.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reestablecer\ncontraseña",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo correo
        OutlinedTextField(
            value = uiState.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            placeholder = { Text("Ingrese Correo Electronico", color = Color.Gray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uiState.errorMessage!!, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones Cancelar y Enviar en la misma fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onCancelarClick,
                modifier = Modifier.weight(1f).height(46.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFe74c3c),
                    contentColor = Color.White
                )
            ) {
                Text("Cancelar", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Button(
                onClick = { viewModel.onEnviarClick() },
                modifier = Modifier.weight(1f).height(46.dp),
                shape = RoundedCornerShape(24.dp),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ecc9a),
                    contentColor = Color.White
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text("Enviar", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RestablecerContrasenaScreenPreview() {
    RestablecerContrasenaScreen()
}