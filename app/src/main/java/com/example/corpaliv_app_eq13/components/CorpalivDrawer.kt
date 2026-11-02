package com.example.corpaliv_app_eq13.components


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorpalivDrawer(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    content: @Composable () -> Unit
) {

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {

                Text(
                    text = "Menú CORPALIV",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.navigateTo(Screen.Home)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Conoce CORPALIV") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.navigateTo(Screen.Us)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Colabora con nosotros") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.navigateTo(Screen.Colaborate)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Mi perfil") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.navigateTo(Screen.Profile)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            mainViewModel.navigateTo(Screen.Settings)
                        }
                    }
                )
            }
        }
    ) {
        content()
    }
}
