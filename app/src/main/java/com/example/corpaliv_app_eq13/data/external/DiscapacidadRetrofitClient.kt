package com.example.corpaliv_app_eq13.data.external

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DiscapacidadRetrofitClient {

    private const val BASE_URL =
        "https://bid-ckan.ministeriodesarrollosocial.gob.cl/"

    val api: DiscapacidadApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(DiscapacidadApi::class.java)
    }
}