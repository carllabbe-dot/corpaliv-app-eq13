
package com.example.corpaliv_app_eq13.viewmodel

import androidx.lifecycle.ViewModel
import com.example.corpaliv_app_eq13.model.UsuarioErrores
import com.example.corpaliv_app_eq13.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado = _estado.asStateFlow()

    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errores = it.errores.copy(clave = null)
            )
        }
    }

    fun onDireccionChange(valor: String) {
        _estado.update {
            it.copy(
                direccion = valor,
                errores = it.errores.copy(direccion = null)
            )
        }
    }

    fun onAceptaTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(aceptaTerminos = valor)
        }
    }
    fun onLogoEmpresaChange(uri: String?) {
        _estado.update {
            it.copy(
                logoEmpresa = uri,
                errores = it.errores.copy(
                    logoEmpresa = null
                )
            )
        }
    }

    fun validarFormulario(): Boolean {

        val datos = _estado.value

        val errores = UsuarioErrores(
            nombre = if (datos.nombre.isBlank())
                "El nombre es obligatorio" else null,

            correo = if (!android.util.Patterns.EMAIL_ADDRESS
                    .matcher(datos.correo).matches())
                "Ingrese un correo válido" else null,

            clave = if (datos.clave.length < 6)
                "La clave debe tener al menos 6 caracteres" else null,

            direccion = if (datos.direccion.isBlank())
                "La dirección es obligatoria" else null,
            logoEmpresa = if (datos.logoEmpresa.isNullOrBlank()) {
                "Debes adjuntar el logo de tu empresa"
            } else {
                null
            }

        )

        _estado.update {
            it.copy(errores = errores)
        }

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        return !hayErrores && datos.aceptaTerminos
    }
}
