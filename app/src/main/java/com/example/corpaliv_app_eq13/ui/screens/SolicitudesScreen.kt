
package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv_app_eq13.components.CorpalivDrawer
import com.example.corpaliv_app_eq13.components.Header
import com.example.corpaliv_app_eq13.data.remote.SolicitudDto
import com.example.corpaliv_app_eq13.data.repository.SolicitudRepository
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SolicitudesScreen(
    viewModel: MainViewModel
) {

    val repository = remember { SolicitudRepository() }
    val scope = rememberCoroutineScope()

    var solicitudes by remember {
        mutableStateOf<List<SolicitudDto>>(emptyList())
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }
    var solicitudSeleccionada by remember {
        mutableStateOf<SolicitudDto?>(null)
    }

    var nombreEditado by remember {
        mutableStateOf("")
    }

    var correoEditado by remember {
        mutableStateOf("")
    }

    var direccionEditada by remember {
        mutableStateOf("")
    }

    var guardandoCambios by remember {
        mutableStateOf(false)
    }

    var errorEdicion by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(Unit) {
        try {
            solicitudes = repository.listarSolicitudes()
        } catch (e: Exception) {
            error = "No se pudieron cargar las solicitudes"
        } finally {
            cargando = false
        }
    }
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )


    CorpalivDrawer(
        drawerState = drawerState,
        mainViewModel = viewModel
    ) {
        Scaffold(
            topBar = {
                Header(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Solicitudes recibidas",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    cargando -> CircularProgressIndicator()

                    error != null -> Text(
                        text = error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )

                    solicitudes.isEmpty() -> Text(
                        text = "No hay solicitudes registradas"
                    )

                    else -> LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(solicitudes) { solicitud ->

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = solicitud.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text("Correo: ${solicitud.correo}")
                                    Text("Dirección: ${solicitud.direccion}")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        onClick = {

                                            solicitudSeleccionada = solicitud

                                            nombreEditado = solicitud.nombre
                                            correoEditado = solicitud.correo
                                            direccionEditada = solicitud.direccion

                                            errorEdicion = null
                                        },
                                        enabled = solicitud.id != null
                                    ) {
                                        Text("Editar")
                                    }
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        onClick = {
                                            scope.launch {
                                                try {
                                                    solicitud.id?.let { id ->

                                                        repository.eliminarSolicitud(id)

                                                        solicitudes = repository.listarSolicitudes()
                                                        error = null
                                                    }
                                                } catch (e: Exception) {
                                                    error = "No se pudo eliminar la solicitud"
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.error
                                        )
                                    ) {
                                        Text("Eliminar")
                                    }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    solicitudSeleccionada?.let { solicitud ->

        AlertDialog(
            onDismissRequest = {
                if (!guardandoCambios) {
                    solicitudSeleccionada = null
                }
            },

            title = {
                Text("Editar solicitud")
            },

            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedTextField(
                        value = nombreEditado,
                        onValueChange = { nombreEditado = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        enabled = !guardandoCambios
                    )

                    OutlinedTextField(
                        value = correoEditado,
                        onValueChange = { correoEditado = it },
                        label = { Text("Correo") },
                        singleLine = true,
                        enabled = !guardandoCambios
                    )

                    OutlinedTextField(
                        value = direccionEditada,
                        onValueChange = { direccionEditada = it },
                        label = { Text("Dirección") },
                        singleLine = true,
                        enabled = !guardandoCambios
                    )

                    errorEdicion?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },

            confirmButton = {

                TextButton(
                    enabled = !guardandoCambios &&
                            nombreEditado.isNotBlank() &&
                            android.util.Patterns.EMAIL_ADDRESS
                                .matcher(correoEditado.trim()).matches() &&
                            direccionEditada.isNotBlank(),

                    onClick = {

                        scope.launch {

                            guardandoCambios = true
                            errorEdicion = null

                            try {

                                val solicitudActualizada = solicitud.copy(
                                    nombre = nombreEditado.trim(),
                                    correo = correoEditado.trim(),
                                    direccion = direccionEditada.trim()
                                )

                                solicitud.id?.let { id ->

                                    val actualizada =
                                        repository.actualizarSolicitud(
                                            id,
                                            solicitudActualizada
                                        )

                                    solicitudes = solicitudes.map {
                                        if (it.id == id) actualizada else it
                                    }

                                    solicitudSeleccionada = null
                                    error = null
                                }

                            } catch (e: Exception) {

                                errorEdicion =
                                    "No se pudo actualizar la solicitud"

                            } finally {

                                guardandoCambios = false
                            }
                        }
                    }
                ) {
                    Text(
                        if (guardandoCambios) "Guardando..."
                        else "Guardar cambios"
                    )
                }
            },

            dismissButton = {

                TextButton(
                    enabled = !guardandoCambios,
                    onClick = {
                        solicitudSeleccionada = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}