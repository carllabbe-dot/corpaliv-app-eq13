package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.corpaliv_app_eq13.navigation.Screen
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
){

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //Hamburguesa
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
    )
    //Abajo la pantalla
    {  Scaffold(
        topBar={
            TopAppBar(
                title ={Text("Pantalla Home")},
                navigationIcon ={
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }){
                        Icon(imageVector= Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text="Bienvenido al inicio (MVVM")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick= {viewModel.navigateTo(Screen.Settings) }){
                Text("Ir a Configuracion")
            }
        }
    }}
}