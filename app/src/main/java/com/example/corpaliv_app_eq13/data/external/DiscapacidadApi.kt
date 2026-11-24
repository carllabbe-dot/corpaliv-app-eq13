package com.example.corpaliv_app_eq13.data.external

import retrofit2.http.GET
import retrofit2.http.Query

interface DiscapacidadApi {

    @GET("api/3/action/package_search")
    suspend fun listarDatos(
        @Query("q") consulta: String = "discapacidad",
        @Query("rows") cantidad: Int = 10
    ): DiscapacidadResponse
}