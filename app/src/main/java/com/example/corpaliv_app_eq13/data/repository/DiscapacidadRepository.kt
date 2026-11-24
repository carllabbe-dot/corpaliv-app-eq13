package com.example.corpaliv_app_eq13.data.repository

import com.example.corpaliv_app_eq13.data.external.DiscapacidadApi
import com.example.corpaliv_app_eq13.data.external.DiscapacidadDto
import com.example.corpaliv_app_eq13.data.external.DiscapacidadRetrofitClient

class DiscapacidadRepository(
    private val api: DiscapacidadApi =
        DiscapacidadRetrofitClient.api
) {

    suspend fun listarDatos(): List<DiscapacidadDto> {

        val respuesta = api.listarDatos()

        if (!respuesta.success) {
            throw IllegalStateException(
                "Error al consultar la API externa"
            )
        }

        return respuesta.result.results
    }
}