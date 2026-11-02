package com.example.corpaliv_app_eq13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.corpaliv_app_eq13.R
import com.example.corpaliv_app_eq13.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import com.example.corpaliv_app_eq13.navigation.Screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.corpaliv_app_eq13.ui.theme.Corpalivappeq13Theme
import com.example.corpaliv_app_eq13.ui.theme.Crema
import com.example.corpaliv_app_eq13.ui.theme.VerdeOscuro
import com.example.corpaliv_app_eq13.ui.theme.VerdePrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()

){
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
             NavigationDrawerItem(
                 label={Text(text="Colabora aquí!")},
                 selected = false,
                 onClick={
                     scope.launch{drawerState.close()}
                     viewModel.navigateTo(Screen.Colaborate)
                 }
             )
         }
     }
 )   {  Scaffold(
        topBar={
            TopAppBar(
                title ={Text("")},
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
                            .width(210.dp)
                            .height(70.dp)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        },


 ) { innerPadding ->

     Column(
         modifier = Modifier
             .padding(innerPadding)
             .fillMaxSize()
             .background(Crema)
             .verticalScroll(rememberScrollState())
             .padding(20.dp),
         verticalArrangement = Arrangement.spacedBy(20.dp)
     ) {

         // TARJETA PRINCIPAL DE BIENVENIDA
         Card(
             modifier = Modifier.fillMaxWidth(),
             shape = RoundedCornerShape(24.dp),
             colors = CardDefaults.cardColors(
                 containerColor = VerdeOscuro
             ),
             elevation = CardDefaults.cardElevation(
                 defaultElevation = 4.dp
             )
         ) {
             Column(
                 modifier = Modifier.padding(24.dp),
                 verticalArrangement = Arrangement.spacedBy(16.dp)
             ) {
                 Text(
                     text = "BIENVENIDO A CORPALIV",
                     color = VerdeOscuro,
                     fontSize = 12.sp,
                     fontWeight = FontWeight.Bold
                 )

                 Text(
                     text = "Juntos podemos hacer la diferencia.",
                     color = MaterialTheme.colorScheme.onSecondary,
                     style = MaterialTheme.typography.headlineMedium,
                     fontWeight = FontWeight.Bold
                 )

                 Text(
                     text = "Conoce nuestra organización y descubre cómo puedes colaborar.",
                     color = Crema,
                     style = MaterialTheme.typography.bodyMedium
                 )

                 Button(
                     onClick = {
                         viewModel.navigateTo(Screen.Us)
                     },
                     colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                         containerColor = MaterialTheme.colorScheme.onSecondary,
                         contentColor = VerdePrincipal
                     )
                 ) {
                     Text("Conoce CORPALIV")
                 }
             }
         }

         // TITULO DE LA SECCIÓN
         Text(
             text = "Explora nuestra comunidad",
             style = MaterialTheme.typography.titleLarge,
             fontWeight = FontWeight.Bold,
             color = VerdeOscuro
         )

         // TARJETA PARA COLABORAR
         Card(
             modifier = Modifier
                 .fillMaxWidth()
                 .clickable {
                     viewModel.navigateTo(Screen.Colaborate)
                 },
             shape = RoundedCornerShape(18.dp),
             colors = CardDefaults.cardColors(
                 containerColor = MaterialTheme.colorScheme.onSecondary
             ),
             elevation = CardDefaults.cardElevation(3.dp)
         ) {
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(20.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(16.dp)
             ) {


                 Column {
                     Text(
                         text = "Colabora con nosotros",
                         fontWeight = FontWeight.Bold,
                         color = VerdeOscuro
                     )
                     Text(
                         text = "Envía tu solicitud de colaboración.",
                         style = MaterialTheme.typography.bodySmall,
                         color = Color.Gray
                     )
                 }
             }
         }

         // TARJETA DE CONFIGURACIÓN
         Card(
             modifier = Modifier
                 .fillMaxWidth()
                 .clickable {
                     viewModel.navigateTo(Screen.Settings)
                 },
             shape = RoundedCornerShape(18.dp),
             colors = CardDefaults.cardColors(
                 containerColor = MaterialTheme.colorScheme.onSecondary
             ),
             elevation = CardDefaults.cardElevation(3.dp)
         ) {
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(20.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(16.dp)
             ) {

                 Column {
                     Text(
                         text = "Configuración",
                         fontWeight = FontWeight.Bold,
                         color = VerdeOscuro
                     )
                     Text(
                         text = "Personaliza tu experiencia.",
                         style = MaterialTheme.typography.bodySmall,
                         color = Color.Gray
                     )
                 }
             }
         }
     }

 }}}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Corpalivappeq13Theme(){
        val navController = rememberNavController()
        HomeScreen(
            navController = navController
        )
    }
}
