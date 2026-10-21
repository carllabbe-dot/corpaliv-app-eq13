package com.example.corpaliv_app_eq13.ui.screens

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