package com.example.corpaliv_app_eq13.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.corpaliv_app_eq13.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    onMenuClick: () -> Unit
) {

            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick
                    ){
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Abrir menu")
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

}

