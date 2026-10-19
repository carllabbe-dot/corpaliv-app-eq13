package com.example.corpaliv_app_eq13.ui

data class FormUiState(
    // Datos del formulario
    val nombre: String = "",
    val email: String = "",
    val mensaje: String = "",
    val aceptaTerminos: Boolean = false,

    // Errores de validación (null significa que no hay error)
    val nombreError: String? = null,
    val emailError: String? = null,
    val mensajeError: String? = null,
    val terminosError: String? = null,

    // Bandera para saber si todo el formulario es válido
    val isFormValid: Boolean = false,

    // Bandera para saber si el usuario ya intentó enviar
    val isSubmitted: Boolean = false
)