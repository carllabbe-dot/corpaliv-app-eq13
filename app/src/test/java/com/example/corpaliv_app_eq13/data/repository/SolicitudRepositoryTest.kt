package com.example.corpaliv_app_eq13.data.repository

import com.example.corpaliv_app_eq13.data.remote.SolicitudApi
import com.example.corpaliv_app_eq13.data.remote.SolicitudDto

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

import kotlinx.coroutines.test.runTest

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SolicitudRepositoryTest {

    private lateinit var api: SolicitudApi
    private lateinit var repository: SolicitudRepository

    private val solicitud = SolicitudDto(
        id = 1L,
        nombre = "Empresa de prueba",
        correo = "empresa@correo.cl",
        direccion = "Santiago",
        logoEmpresa = "logo.png"
    )

    @Before
    fun setUp() {

        api = mockk()

        repository = SolicitudRepository(api)
    }

    @Test
    fun crearSolicitud() = runTest {

        coEvery {
            api.crearSolicitud(solicitud)
        } returns solicitud

        val resultado =
            repository.crearSolicitud(solicitud)

        assertEquals(solicitud, resultado)

        coVerify(exactly = 1) {
            api.crearSolicitud(solicitud)
        }
    }

    @Test
    fun listarSolicitudes() = runTest {

        coEvery {
            api.listarSolicitudes()
        } returns listOf(solicitud)

        val resultado =
            repository.listarSolicitudes()

        assertEquals(1, resultado.size)
        assertEquals(solicitud, resultado.first())

        coVerify(exactly = 1) {
            api.listarSolicitudes()
        }
    }

    @Test
    fun listarSolicitudesVacias() = runTest {

        coEvery {
            api.listarSolicitudes()
        } returns emptyList()

        val resultado =
            repository.listarSolicitudes()

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun buscarSolicitud() = runTest {

        coEvery {
            api.buscarSolicitud(1L)
        } returns solicitud

        val resultado =
            repository.buscarSolicitud(1L)

        assertEquals(solicitud, resultado)
    }

    @Test
    fun actualizarSolicitud() = runTest {

        val actualizada = solicitud.copy(
            nombre = "Empresa actualizada"
        )

        coEvery {
            api.actualizarSolicitud(1L, actualizada)
        } returns actualizada

        val resultado =
            repository.actualizarSolicitud(
                1L,
                actualizada
            )

        assertEquals(actualizada, resultado)

        coVerify(exactly = 1) {
            api.actualizarSolicitud(1L, actualizada)
        }
    }

    @Test
    fun eliminarSolicitud() = runTest {

        coEvery {
            api.eliminarSolicitud(1L)
        } returns Response.success(Unit)

        repository.eliminarSolicitud(1L)

        coVerify(exactly = 1) {
            api.eliminarSolicitud(1L)
        }
    }

    @Test
    fun errorAlEliminarSolicitud() = runTest {

        coEvery {
            api.eliminarSolicitud(1L)
        } returns Response.error<Unit>(
            404,
            "Solicitud no encontrada".toResponseBody()
        )

        val error = runCatching {
            repository.eliminarSolicitud(1L)
        }.exceptionOrNull()

        assertNotNull(error)

        assertEquals(
            "Error al eliminar: HTTP 404",
            error?.message
        )
    }
}