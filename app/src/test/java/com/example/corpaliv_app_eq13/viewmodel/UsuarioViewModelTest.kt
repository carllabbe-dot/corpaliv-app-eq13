package com.example.corpaliv_app_eq13.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UsuarioViewModelTest {

    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        viewModel = UsuarioViewModel()
    }

    private fun completarFormulario() {
        viewModel.onNombreChange("Empresa de prueba")
        viewModel.onCorreoChange("empresa@correo.cl")
        viewModel.onClaveChange("123456")
        viewModel.onDireccionChange("Santiago")
        viewModel.onLogoEmpresaChange("logo.png")
        viewModel.onAceptaTerminosChange(true)
    }

    @Test
    fun formularioValido() {
        completarFormulario()
        assertTrue(viewModel.validarFormulario())
    }

    @Test
    fun nombreVacio() {
        completarFormulario()
        viewModel.onNombreChange("")
        assertFalse(viewModel.validarFormulario())
        assertNotNull(
            viewModel.estado.value.errores.nombre
        )
    }

    @Test
    fun correoInvalido() {
        completarFormulario()
        viewModel.onCorreoChange("correo-invalido")
        assertFalse(viewModel.validarFormulario())
        assertNotNull(
            viewModel.estado.value.errores.correo
        )
    }

    @Test
    fun claveDemasiadoCorta() {
        completarFormulario()
        viewModel.onClaveChange("123")
        assertFalse(viewModel.validarFormulario())
        assertNotNull(
            viewModel.estado.value.errores.clave
        )
    }

    @Test
    fun direccionVacia() {
        completarFormulario()
        viewModel.onDireccionChange("")
        assertFalse(viewModel.validarFormulario())
        assertNotNull(
            viewModel.estado.value.errores.direccion
        )
    }
    @Test
    fun logoNoSeleccionado() {
        completarFormulario()
        viewModel.onLogoEmpresaChange(null)
        assertFalse(viewModel.validarFormulario())
        assertNotNull(
            viewModel.estado.value.errores.logoEmpresa
        )
    }
    @Test
    fun terminosNoAceptados() {
        completarFormulario()
        viewModel.onAceptaTerminosChange(false)
        assertFalse(viewModel.validarFormulario())
    }
}