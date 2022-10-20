package com.vasilis.eventbook.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vasilis.eventbook.ui.home.HomeScreen
import com.vasilis.eventbook.ui.TopBar

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
@Composable
fun EventBookApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar()
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = "Home") {
                HomeScreen(viewModel = hiltViewModel())
            }
        }
    }
}