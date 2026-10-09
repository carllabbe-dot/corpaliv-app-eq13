package com.example.corpaliv_app_eq13.ui.screens
/**
import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable


import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsScreen(
    navController: NavController,
    viewModel: MainViewModel,
){

    Scaffold(
    topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Nosotros")
            }
        )
    },
    bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Corpaliv APP®",
            )
        }
    },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
            Text(
                text="Bienvenido a CORPALIV®",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Justify

            )
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                            "CORPALIV es una organizacion sin " +
                            "fines de lucro dedicada a promover la autonomia" +
                            "participacion e inclusion social y laboral" +
                            "de personas con discapacidad multiple",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Nuestro propósito",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Promover oportunidades de formación, " +
                                "autonomía y participación social.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Nuestros programas",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "Escuela Especial Jan Van Dijk",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Red Sociolaboral Alba Lab",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    // Sección final
                    Text(
                        text = "¡Conoce cómo puedes colaborar!",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            // Posteriormente agregaremos navegación
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Conoce cómo colaborar")
                    }
                }
            }
        }

    }
    }

**/


import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.corpaliv_app_eq13.R
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.ui.theme.Corpalivappeq13Theme
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "Menu",modifier = Modifier.padding(all=16.dp))
                NavigationDrawerItem(
                    label={Text(text="Ir al Perfil")},
                    selected = false,
                    onClick={
                        scope.launch{drawerState.close()}
                        viewModel.navigateTo(Screen.Profile)
                    }
                )
            }
        }
    ){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Nosotros")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon ={
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }){
                        Icon(imageVector= Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    Image(
                        painter = painterResource(R.drawable.logo_corpaliv),
                        contentDescription = "Logo Corpaliv",
                        modifier = Modifier
                            .width(120.dp)
                            .height(70.dp)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Fit
                    )
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Título principal
            Text(
                text = "Construimos oportunidades de inclusión",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Descripción institucional
            Text(
                text = "CORPALIV es una organización sin fines " +
                        "de lucro dedicada a promover la autonomía, " +
                        "participación e inclusión social y laboral " +
                        "de personas con discapacidad múltiple.",
                style = MaterialTheme.typography.bodyLarge
            )

            // Tarjeta: Nuestro propósito

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Nuestro propósito",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Promover oportunidades de formación, " +
                                "autonomía y participación social.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.secondaryContainer
                        )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Nuestros programas",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Escuela Especial Jan Van Dijk",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Red Sociolaboral Alba Lab",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }


            Text(
                text = "¡Conoce cómo puedes colaborar!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.navigateTo(Screen.Colaborate)
                },
                modifier = Modifier.widthIn(max = 300.dp)
                    .align(Alignment.CenterHorizontally),


            ) {
                Text(text="Hazte socio",
                    style= MaterialTheme.typography.titleLarge)
            }
            OutlinedButton(
                onClick = {
                    viewModel.navigateBack()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )

            ){
                Text("Volver al Inicio")
            }
        }
    }
}}



@Preview(
    showBackground = true,
    name = "Nosotros CORPALIV"
)
@Composable
fun UsScreenPreview() {
    Corpalivappeq13Theme {
        val navController = rememberNavController()
        val viewModel: MainViewModel = viewModel()

        UsScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}