package com.example.petsall

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeViewModel
import com.example.petsall.ui.changepet.PAChangePet
import com.example.petsall.ui.emergency.PAEmergency
import com.example.petsall.ui.home.PAHome
import com.example.petsall.ui.theme.PetsAllTheme
import com.example.petsall.ui.login.PALogin
import com.example.petsall.ui.navigation.Menu
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.newPet.PANewPet
import com.example.petsall.ui.perfil.PAPerfil
import com.example.petsall.ui.signup.PASignUp
import com.example.petsall.ui.vet.PAVet
import com.example.petsall.ui.vetdetail.PAVetDetail
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    var permissionRequestCount = 0
    private lateinit var requestMultiplePermissions: ActivityResultLauncher<Array<String>>

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMultiplePermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val allPermissionsGranted = permissions.values.all { it }
                if (allPermissionsGranted) {
                } else {
                    // Al menos uno de los permisos ha sido denegado
                    // Realizar acciones necesarias
                    false
                }
            }

        setContent {
            PetsAllTheme {
                val navigationController = rememberNavController()
                val user = Firebase.auth.currentUser
                val items = listOf(
                    Menu(Route.PAHome, Icons.Filled.Home, "Inicio"),
                    Menu(Route.PAVet, Icons.Filled.Favorite, "Veterinario"),
                    Menu(Route.PAEmergency, Icons.Filled.Warning, "Emergencia"),
                    Menu(Route.PAPerfil, Icons.Filled.Person, "Perfil"),
                )
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Scaffold(bottomBar = {
                            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            if (currentDestination?.route in listOf(
                                    Route.PAHome, Route.PAEmergency, Route.PAVet, Route.PAPerfil

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
                                                    item.icon, contentDescription = null
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
                                            })
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
                                    activity = this@MainActivity,
                                    requestMultiplePermissions = requestMultiplePermissions
                                )
                            }
                            composable(Route.PASignUp) { PASignUp(navController = navigationController) }
                            composable(Route.PAVet) { PAVet(navController = navigationController) }
                            composable(Route.PAEmergency) { PAEmergency() }
                            composable(Route.PANewPet) { PANewPet(navController = navigationController) }
                            composable(Route.PAPerfil) { PAPerfil(navController = navigationController) }
                            composable(
                                "${Route.PAChangePet}/{petId}",
                                arguments = listOf(navArgument("petId") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("petId")?.let {
                                    PAChangePet(
                                        idPet = it,
                                        navController = navigationController,
                                    )
                                }
                            }
                            composable(
                                "${Route.PAVetDetail}/{vetId}",
                                arguments = listOf(navArgument("vetId") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("vetId")?.let {
                                    PAVetDetail(
                                        vetDetail = it,
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

    fun incrementPermissionRequestCount() {
        permissionRequestCount++
    }


}



