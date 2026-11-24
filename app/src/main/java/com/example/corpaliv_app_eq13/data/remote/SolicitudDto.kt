
package com.example.corpaliv_app_eq13.data.remote

data class SolicitudDto(
    val id: Long? = null,
    val nombre: String,
    val correo: String,
    val direccion: String,
    val logoEmpresa: String? = null
)
