package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv_app_eq13.components.CorpalivDrawer
import com.example.corpaliv_app_eq13.components.Header
import com.example.corpaliv_app_eq13.data.external.DiscapacidadDto
import com.example.corpaliv_app_eq13.viewmodel.DiscapacidadViewModel
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscapacidadScreen(
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    viewModel: DiscapacidadViewModel = viewModel()
) {

    val scope = rememberCoroutineScope()
    val estado by viewModel.estado.collectAsState()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    CorpalivDrawer(
        drawerState = drawerState,
        mainViewModel = mainViewModel
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
            },
            containerColor = Color(0xFFF7F4EE)

        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Discapacidad en Chile",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF3F6B5A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Consulta información pública sobre discapacidad e inclusión en Chile."
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Fuente: Ministerio de Desarrollo Social y Familia",
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    when {

                        estado.cargando -> {

                            CircularProgressIndicator(
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }

                        estado.error != null -> {

                            Column(
                                modifier = Modifier.align(
                                    Alignment.Center
                                ),
                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = estado.error.orEmpty()
                                )

                                Button(
                                    onClick = {
                                        viewModel.cargarDatos()
                                    }
                                ) {
                                    Text("Reintentar")
                                }
                            }
                        }

                        estado.datos.isEmpty() -> {

                            Text(
                                text = "No se encontraron resultados",
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }

                        else -> {

                            LazyColumn(
                                verticalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {

                                items(
                                    items = estado.datos,
                                    key = { it.id }
                                ) { dato ->

                                    TarjetaDiscapacidad(dato)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F6B5A)
                    )
                ) {
                    Text("Volver")
                }

            }

        }

    }

}


@Composable
fun TarjetaDiscapacidad(
    dato: DiscapacidadDto
) {
    val uriHandler = LocalUriHandler.current
    var errorEnlace by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD5EBDF)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = dato.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF233B32)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dato.notes
                    ?.takeIf { it.isNotBlank() }
                    ?: "Información pública sobre discapacidad.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            dato.resources.forEach { recurso ->
                OutlinedButton(
                    onClick = {
                        errorEnlace = false
                        try {
                            uriHandler.openUri(recurso.url)
                        } catch (e: Exception) {
                            errorEnlace = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Consultar documento (${recurso.format ?: "Archivo"})"
                    )
                }
            }
            if (errorEnlace) {
                Text(
                    text = "No se pudo abrir el documento",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}