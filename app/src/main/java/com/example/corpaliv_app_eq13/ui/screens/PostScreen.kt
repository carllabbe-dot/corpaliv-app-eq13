package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.corpaliv_app_eq13.R
import com.example.corpaliv_app_eq13.components.CorpalivDrawer
import com.example.corpaliv_app_eq13.data.model.Post
import com.example.corpaliv_app_eq13.ui.theme.Crema
import com.example.corpaliv_app_eq13.ui.theme.GrisOscuro
import com.example.corpaliv_app_eq13.ui.theme.VerdeClaro
import com.example.corpaliv_app_eq13.ui.theme.VerdeOscuro
import com.example.corpaliv_app_eq13.ui.theme.VerdePrincipal
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import com.example.corpaliv_app_eq13.viewmodel.PostViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    postViewModel: PostViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val uiState by postViewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var nuevoTitulo by remember { mutableStateOf("") }
    var nuevoCuerpo by remember { mutableStateOf("") }

    CorpalivDrawer(
        drawerState = drawerState,
        mainViewModel = mainViewModel
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Novedades y Noticias",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = VerdeOscuro
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = VerdeOscuro
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { postViewModel.fetchPosts() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Recargar posts",
                                tint = VerdeOscuro
                            )
                        }
                        Image(
                            painter = painterResource(R.drawable.logo_corpaliv),
                            contentDescription = "Logo Corpaliv",
                            modifier = Modifier
                                .width(120.dp)
                                .height(50.dp)
                                .padding(end = 8.dp),
                            contentScale = ContentScale.Fit
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Crema)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = VerdePrincipal,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Nueva publicación")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Crema)
                    .padding(16.dp)
            ) {
                // Header explicativo
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = VerdeClaro),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Publicaciones de la Comunidad (API REST Externa)",
                            fontWeight = FontWeight.Bold,
                            color = VerdeOscuro,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Consumo remoto con Retrofit desde JSONPlaceholder en tiempo real.",
                            fontSize = 13.sp,
                            color = GrisOscuro
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mensajes de éxito o error
                uiState.errorMessage?.let { error ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp),
                            fontSize = 13.sp
                        )
                    }
                }

                uiState.successMessage?.let { success ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = VerdeClaro)
                    ) {
                        Text(
                            text = success,
                            color = VerdeOscuro,
                            modifier = Modifier.padding(12.dp),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Estado de carga o Lista
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = VerdePrincipal)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(uiState.posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onDelete = { postViewModel.deletePost(post.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo para crear un nuevo post (Demostración de POST)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nueva Publicación (HTTP POST)") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nuevoTitulo,
                        onValueChange = { nuevoTitulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevoCuerpo,
                        onValueChange = { nuevoCuerpo = it },
                        label = { Text("Contenido / Mensaje") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        postViewModel.createPost(nuevoTitulo, nuevoCuerpo)
                        nuevoTitulo = ""
                        nuevoCuerpo = ""
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdePrincipal)
                ) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun PostCard(
    post: Post,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${post.id} ${post.title}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = VerdeOscuro,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar post",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = GrisOscuro
            )
        }
    }
}
