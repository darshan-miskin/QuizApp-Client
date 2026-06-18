package com.darshan.miskin.quizapp_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Score
import androidx.compose.material.icons.filled.Scoreboard
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darshan.miskin.quizapp_client.ui.theme.QuizApp_ClientTheme

class MainActivity : ComponentActivity() {

    enum class DESTINATIONS(
        val route: String,
        val label: String,
        val icon: ImageVector,
        val description: String,
        val screen: @Composable () -> Unit
    ) {
        QUIZ_SCREEN("quiz_screen", "Quiz", Icons.Default.School, "", {Text("Quiz Screen!!")}),
        RESULT_SCREEN("result_screen", "Result", Icons.Default.BarChart, "", {Text("Result Screen!")}),
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            QuizApp_ClientTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableStateOf(MainActivity.DESTINATIONS.QUIZ_SCREEN.route) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                MainActivity.DESTINATIONS.entries.forEach {
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
        NavHost(modifier = Modifier.padding(innerPadding), navController = navController, startDestination = MainActivity.DESTINATIONS.QUIZ_SCREEN.route) {
            MainActivity.DESTINATIONS.entries.forEach { destination ->
                composable(destination.route) {
                    destination.screen.invoke()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuizApp_ClientTheme {
        MainScreen()
    }
}