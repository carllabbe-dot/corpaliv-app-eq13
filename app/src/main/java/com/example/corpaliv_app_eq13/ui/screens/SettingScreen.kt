
package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.corpaliv_app_eq13.components.CorpalivDrawer
import com.example.corpaliv_app_eq13.components.Header
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    // Estado del menú lateral
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    CorpalivDrawer(
        drawerState = drawerState,
        mainViewModel = viewModel
    )  {
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pantalla de configuración",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Home)
                    }
                ) {
                    Text("Volver al Inicio")
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Button(
                    onClick = {
                        viewModel.navigateTo(Screen.Profile)
                    }
                ) {
                    Text("Ir al Perfil")
                }
            }
        }
    }
}
