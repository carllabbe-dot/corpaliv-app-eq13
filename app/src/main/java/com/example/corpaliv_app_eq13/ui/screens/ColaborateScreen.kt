


package com.example.corpaliv_app_eq13.ui.screens

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.corpaliv_app_eq13.components.CorpalivDrawer
import com.example.corpaliv_app_eq13.components.Header
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.ui.theme.VerdeOscuro
import com.example.corpaliv_app_eq13.ui.theme.VerdePrincipal
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



    fun tienePermisoFotos(): Boolean {
        return permisosFotos.any { permiso ->
            ContextCompat.checkSelfPermission(
                context,
                permiso
            ) == PackageManager.PERMISSION_GRANTED
        }
    }



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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
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


            bottomBar = {
                BottomAppBar(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "CORPALIV · Alba Lab",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        ) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
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

        mensajePermiso?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }


        estado.errores.logoEmpresa?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }


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


        var guardando by remember { mutableStateOf(false) }
        var errorGuardado by remember { mutableStateOf(false) }

        Button(
            onClick = {
                intentoEnviar = true
                formularioValido = false
                errorGuardado = false

                if (usuarioViewModel.validarFormulario()) {
                    guardando = true

                    usuarioViewModel.guardarSolicitud(context) { exito ->
                        guardando = false
                        formularioValido = exito
                        errorGuardado = !exito
                    }
                }
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (guardando) "Guardando..." else "Enviar solicitud")
        }

        if (errorGuardado) {
            Text(
                text = "No fue posible guardar la solicitud.",
                color = MaterialTheme.colorScheme.error
            )
        }


        if (formularioValido) {

            Dialog(
                onDismissRequest = {
                    formularioValido = false
                }
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        AnimatedVisibility(
                            visible = formularioValido,
                            enter = fadeIn() + scaleIn()
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Guardado correcto",
                                tint = Color(0xFF238552),
                                modifier = Modifier.size(72.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "¡Guardado exitosamente!",
                            style = MaterialTheme.typography.titleLarge,
                            color = VerdePrincipal
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Tu solicitud ha sido guardada correctamente.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                formularioValido = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                }
            }
        }
        OutlinedButton(
            onClick = {
                mainViewModel.navigateTo(Screen.Home)
            },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = VerdeOscuro
            )
        ) {
            Text(
                text = "Volver al inicio",
                color = VerdeOscuro
            )
        }
    }}}}
