package com.example.corpaliv_app_eq13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv_app_eq13.data.external.DiscapacidadDto
import com.example.corpaliv_app_eq13.data.repository.DiscapacidadRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DiscapacidadUiState(
    val datos: List<DiscapacidadDto> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)

class DiscapacidadViewModel : ViewModel() {
    private val repository = DiscapacidadRepository()
    private val _estado =
        MutableStateFlow(DiscapacidadUiState())

    val estado = _estado.asStateFlow()
    init {
        cargarDatos()
    }
    fun cargarDatos() {
        viewModelScope.launch {

            _estado.update {
                it.copy(
                    cargando = true,
                    error = null
                )
            }
            try {
                val resultado = repository.listarDatos()

                _estado.update {
                    it.copy(
                        datos = resultado,
                        cargando = false
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo cargar la información"
                    )
                }
            }
        }
    }
}