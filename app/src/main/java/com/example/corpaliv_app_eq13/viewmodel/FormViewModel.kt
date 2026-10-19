package com.example.corpaliv_app_eq13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.corpaliv_app_eq13.ui.FormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormViewModel : ViewModel() {

    // 1. Estado privado que solo el ViewModel puede modificar
    private val _uiState = MutableStateFlow(FormUiState())

    // 2. Estado público de solo lectura para que la UI lo observe
    val uiState: StateFlow<FormUiState> = _uiState.asStateFlow()

    // --- FUNCIONES QUE LA UI LLAMARÁ ---

    fun onNombreChanged(nombre: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nombre = nombre,
                nombreError = validarNombre(nombre)
            )
        }
        validarFormularioCompleto()
    }

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                emailError = validarEmail(email)
            )
        }
        validarFormularioCompleto()
    }

    fun onMensajeChanged(mensaje: String) {
        _uiState.update { currentState ->
            currentState.copy(
                mensaje = mensaje,
                mensajeError = validarMensaje(mensaje)
            )
        }
        validarFormularioCompleto()
    }

    fun onTerminosChanged(acepta: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                aceptaTerminos = acepta,
                terminosError = if (!acepta) "Debes aceptar los términos" else null
            )
        }
        validarFormularioCompleto()
    }

    // --- LÓGICA DE VALIDACIÓN (Privada, el UI no la ve) ---

    private fun validarNombre(nombre: String): String? {
        return if (nombre.isBlank()) "El nombre no puede estar vacío"
        else if (nombre.length < 3) "El nombre es muy corto"
        else null
    }

    private fun validarEmail(email: String): String? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return when {
            email.isBlank() -> "El correo no puede estar vacío"
            !email.matches(emailRegex) -> "El formato del correo no es válido"
            else -> null
        }
    }

    private fun validarMensaje(mensaje: String): String? {
        return if (mensaje.isBlank()) "El mensaje no puede estar vacío"
        else if (mensaje.length < 10) "El mensaje debe tener al menos 10 caracteres"
        else null
    }

    private fun validarFormularioCompleto() {
        _uiState.update { currentState ->
            val esValido = currentState.nombreError == null && currentState.nombre.isNotBlank() &&
                    currentState.emailError == null && currentState.email.isNotBlank() &&
                    currentState.mensajeError == null && currentState.mensaje.isNotBlank() &&
                    currentState.aceptaTerminos

            currentState.copy(isFormValid = esValido)
        }
    }
}