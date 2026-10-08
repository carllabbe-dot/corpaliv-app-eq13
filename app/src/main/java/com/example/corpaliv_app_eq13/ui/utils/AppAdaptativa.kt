package com.example.corpaliv_app_eq13.ui.utils


import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun AppAdaptativa(
    windowSize: WindowSizeClass
){
    when(windowSize.widthSizeClass){
        WindowWidthSizeClass.Compact -> PantallaCompacta()
        WindowWidthSizeClass.Medium -> PantallaMediana()
        WindowWidthSizeClass.Expanded -> PantallaExpandida()

    }
}

