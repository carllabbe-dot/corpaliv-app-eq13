


package com.example.corpaliv_app_eq13.ui.screens

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import com.example.corpaliv_app_eq13.viewmodel.UsuarioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ColaborateScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel = viewModel()
){
    val estado by usuarioViewModel.estado.collectAsState()

    var formularioValido by remember {
        mutableStateOf(false)
    }

    var intentoEnviar by remember {
        mutableStateOf(false)
    }

    // PERMISOS PARA ACCEDER A LAS IMÁGENES

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val permisosFotos = when {
        Build.VERSION.SDK_INT >= 34 -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        )

        Build.VERSION.SDK_INT >= 33 -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )

        else -> arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    var mostrarGaleria by remember {
        mutableStateOf(false)
    }

    var mensajePermiso by remember {
        mutableStateOf<String?>(null)
    }

    var imagenes by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    var cargandoImagenes by remember {
        mutableStateOf(false)
    }

    // COMPROBAR SI TENEMOS AUTORIZACIÓN

    fun tienePermisoFotos(): Boolean {
        return permisosFotos.any { permiso ->
            ContextCompat.checkSelfPermission(
                context,
                permiso
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    // CONSULTAR FOTOS AUTORIZADAS CON MEDIASTORE

    fun abrirGaleria() {
        mensajePermiso = null
        mostrarGaleria = true
        cargandoImagenes = true

        scope.launch {
            imagenes = withContext(Dispatchers.IO) {
                val resultado = mutableListOf<Uri>()

                val coleccion =
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                val columnas = arrayOf(
                    MediaStore.Images.Media._ID
                )

                try {
                    context.contentResolver.query(
                        coleccion,
                        columnas,
                        null,
                        null,
                        "${MediaStore.Images.Media.DATE_ADDED} DESC"
                    )?.use { cursor ->

                        val indice = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media._ID
                        )

                        while (
                            cursor.moveToNext() &&
                            resultado.size < 100
                        ) {
                            val id = cursor.getLong(indice)

                            val uri = ContentUris.withAppendedId(
                                coleccion,
                                id
                            )

                            resultado.add(uri)
                        }
                    }
                } catch (e: SecurityException) {
                    // El acceso puede haber sido revocado.
                }

                resultado
            }

            cargandoImagenes = false
        }
    }

    // SOLICITAR PERMISOS A ANDROID

    val solicitarPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->

        val autorizado = permisos.values.any { it } ||
                tienePermisoFotos()

        if (autorizado) {
            abrirGaleria()
        } else {
            mensajePermiso =
                "Debes autorizar el acceso a las fotografías."
        }
    }

    // GALERÍA PROPIA

    if (mostrarGaleria) {
        AlertDialog(
            onDismissRequest = {
                mostrarGaleria = false
            },

            title = {
                Text("Selecciona el logo de tu empresa")
            },

            text = {
                if (cargandoImagenes) {
                    CircularProgressIndicator()
                } else if (imagenes.isEmpty()) {
                    Text(
                        "No se encontraron imágenes autorizadas."
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(
                            max = 400.dp
                        ),
                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {
                        items(imagenes) { uri ->

                            AsyncImage(
                                model = uri,
                                contentDescription =
                                    "Seleccionar imagen",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable {
                                        usuarioViewModel.onLogoEmpresaChange(
                                            uri.toString()
                                        )

                                        formularioValido = false
                                        mostrarGaleria = false
                                    },
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarGaleria = false
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Hazte socio de CORPALIV",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Completa tus datos para solicitar tu inscripción."
        )

        // NOMBRE

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = {
                usuarioViewModel.onNombreChange(it)
                formularioValido = false
            },
            label = { Text("Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // CORREO

        OutlinedTextField(
            value = estado.correo,
            onValueChange = {
                usuarioViewModel.onCorreoChange(it)
                formularioValido = false
            },
            label = { Text("Correo electrónico") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // CONTRASEÑA

        OutlinedTextField(
            value = estado.clave,
            onValueChange = {
                usuarioViewModel.onClaveChange(it)
                formularioValido = false
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // DIRECCIÓN

        OutlinedTextField(
            value = estado.direccion,
            onValueChange = {
                usuarioViewModel.onDireccionChange(it)
                formularioValido = false
            },
            label = { Text("Dirección") },
            isError = estado.errores.direccion != null,
            supportingText = {
                estado.errores.direccion?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // LOGO DE LA EMPRESA

        Text(
            text = "Logo de tu empresa *",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Selecciona una imagen del logo de tu empresa."
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {

                    if (tienePermisoFotos()) {
                        abrirGaleria()
                    } else {
                        solicitarPermisos.launch(
                            permisosFotos
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {

            if (estado.logoEmpresa == null) {

                Text(
                    text = "Presiona aquí para cargar tu logo"
                )

            } else {

                AsyncImage(
                    model = estado.logoEmpresa,
                    contentDescription = "Logo de la empresa",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // MENSAJE DE PERMISOS

        mensajePermiso?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // ERROR DEL LOGO

        estado.errores.logoEmpresa?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // TÉRMINOS Y CONDICIONES

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = estado.aceptaTerminos,
                onCheckedChange = {
                    usuarioViewModel.onAceptaTerminosChange(it)
                    formularioValido = false
                }
            )

            Text("Acepto los términos y condiciones")
        }

        if (intentoEnviar && !estado.aceptaTerminos) {
            Text(
                text = "Debes aceptar los términos y condiciones",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        HorizontalDivider()

        // ENVIAR SOLICITUD

        Button(
            onClick = {
                intentoEnviar = true
                formularioValido = usuarioViewModel.validarFormulario()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar solicitud")
        }

        if (formularioValido) {
            Text(
                text = "Formulario validado correctamente.",
                color = MaterialTheme.colorScheme.primary
            )
        }
        Button(
            onClick = {
                mainViewModel.navigateTo(Screen.Home)


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
