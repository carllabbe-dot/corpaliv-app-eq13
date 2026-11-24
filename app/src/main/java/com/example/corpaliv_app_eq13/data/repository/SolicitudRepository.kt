
package com.example.corpaliv_app_eq13.data.repository

import com.example.corpaliv_app_eq13.data.remote.RetrofitClient
import com.example.corpaliv_app_eq13.data.remote.SolicitudApi
import com.example.corpaliv_app_eq13.data.remote.SolicitudDto

class SolicitudRepository(
    private val api: SolicitudApi = RetrofitClient.api
) {
    suspend fun crearSolicitud(
        solicitud: SolicitudDto
    ): SolicitudDto {
        return api.crearSolicitud(solicitud)
    }
    suspend fun listarSolicitudes(): List<SolicitudDto> {
        return api.listarSolicitudes()
    }
    suspend fun buscarSolicitud(id: Long): SolicitudDto {
        return api.buscarSolicitud(id)
    }
    suspend fun actualizarSolicitud(
        id: Long,
        solicitud: SolicitudDto
    ): SolicitudDto {
        return api.actualizarSolicitud(id, solicitud)
    }

    suspend fun eliminarSolicitud(id: Long) {
        val respuesta = api.eliminarSolicitud(id)
        if (!respuesta.isSuccessful) {
            throw Exception(
                "Error al eliminar: HTTP ${respuesta.code()}"
            )
        }
    }
}
