package com.darshan.miskin.quizapp_client

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableStateOf(DESTINATIONS.QUIZ_SCREEN.route) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                DESTINATIONS.entries.forEach {
                    NavigationBarItem(
                        selected = selectedDestination == it.route,
                        onClick = {
                            navController.navigate(route = it.route)
                            selectedDestination = it.route
                        },
                        icon = {
                            Icon(it.icon, contentDescription = it.description)
                        },
                        label = {
                            Text(
                                text = it.label
                            )
                        },
                        alwaysShowLabel = true,
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(modifier = Modifier.padding(innerPadding), navController = navController, startDestination = DESTINATIONS.QUIZ_SCREEN.route) {
            DESTINATIONS.entries.forEach { destination ->
                composable(destination.route) {
                    destination.screen.invoke()
                }
            }
        }
    }
}