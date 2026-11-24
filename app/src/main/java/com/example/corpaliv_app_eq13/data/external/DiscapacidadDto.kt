package com.example.corpaliv_app_eq13.data.external

data class DiscapacidadResponse(
    val success: Boolean,
    val result: DiscapacidadResult
)

data class DiscapacidadResult(
    val count: Int,
    val results: List<DiscapacidadDto>
)

data class DiscapacidadDto(
    val id: String,
    val title: String,
    val notes: String?,
    val resources: List<RecursoDto>
)

data class RecursoDto(
    val name: String?,
    val format: String?,
    val url: String
)