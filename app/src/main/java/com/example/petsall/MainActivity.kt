package com.example.petsall

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.petsall.ui.changepet.PAChangePet
import com.example.petsall.ui.home.PAHome
import com.example.petsall.ui.theme.PetsAllTheme
import com.example.petsall.ui.login.PALogin
import com.example.petsall.ui.navigation.Menu
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.newPet.PANewPet
import com.example.petsall.ui.perfil.PAPerfil
import com.example.petsall.ui.signup.PASignUp
import com.example.petsall.ui.vet.PAVet
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetsAllTheme {
                val navigationController = rememberNavController()
                val user = Firebase.auth.currentUser
                val items = listOf(
                    Menu(Route.PAHome, Icons.Filled.Home, "Inicio"),
                    Menu(Route.PAVet, Icons.Filled.Favorite, "Veterinario"),
                    Menu(Route.PAPerfil, Icons.Filled.Person, "Perfil")
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.route in listOf(
                                Route.PAHome,
                                Route.PAVet,
                                Route.PAPerfil
                            )
                        ) {
                            BottomNavigation {
                                items.forEach { item ->
                                    BottomNavigationItem(modifier = Modifier.background(
                                        Color(
                                            0xff84B1B8
                                        )
                                    ),
                                        icon = {
                                            Icon(
                                                item.icon,
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(item.label) },
                                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                        onClick = {
                                            if (user != null || item.route != Route.PALogin) {
                                                navigationController.navigate(item.route) {
                                                    popUpTo(navigationController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }) { innerPadding ->
                        NavHost(
                            navController = navigationController,
                            startDestination = if (user != null) Route.PAHome else Route.PALogin,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Route.PALogin) { PALogin(navController = navigationController) }
                            composable(Route.PAHome) {
                                PAHome(
                                    navController = navigationController,
                                    activity = this@MainActivity
                                )
                            }
                            composable(Route.PASignUp) { PASignUp(navController = navigationController) }
                            composable(Route.PAVet) { PAVet(navController = navigationController) }
                            composable(Route.PANewPet) { PANewPet(navController = navigationController) }
                            composable(Route.PAPerfil) { PAPerfil(navController = navigationController) }
                            composable(
                                "${Route.PAChangePet}/{petId}",
                                arguments = listOf(navArgument("petId") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("petId")
                                    ?.let {
                                        PAChangePet(
                                            idPet = it,
                                            navController = navigationController,
                                        )
                                    }
                            }


                        }
                    }


                }
            }
        }
    }
}

