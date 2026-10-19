package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv_app_eq13.viewmodel.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormsScreen(
    viewModel: FormViewModel = viewModel()
) {
    // 1. La UI observa el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Formulario de Contacto",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- CAMPO NOMBRE ---
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { viewModel.onNombreChanged(it) },
            label = { Text("Nombre completo") },
            isError = uiState.nombreError != null,
            supportingText = {
                if (uiState.nombreError != null) {
                    Text(text = uiState.nombreError!!, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO EMAIL ---
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Correo electrónico") },
            isError = uiState.emailError != null,
            supportingText = {
                if (uiState.emailError != null) {
                    Text(text = uiState.emailError!!, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO MENSAJE ---
        OutlinedTextField(
            value = uiState.mensaje,
            onValueChange = { viewModel.onMensajeChanged(it) },
            label = { Text("Mensaje o tipo de colaboración") },
            isError = uiState.mensajeError != null,
            supportingText = {
                if (uiState.mensajeError != null) {
                    Text(text = uiState.mensajeError!!, color = MaterialTheme.colorScheme.error)
                }
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- CHECKBOX TÉRMINOS ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.aceptaTerminos,
                onCheckedChange = { viewModel.onTerminosChanged(it) }
            )
            Text(text = "Acepto los términos y condiciones")
        }

        if (uiState.terminosError != null) {
            Text(
                text = uiState.terminosError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN ENVIAR ---
        Button(
            onClick = { /* Aquí irá la navegación al resumen luego */ },
            enabled = uiState.isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Solicitud")
        }
    }
}