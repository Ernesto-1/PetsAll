package com.example.petsall

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.ui.business.PABusinessList
import com.example.petsall.ui.changepet.PAChangePet
import com.example.petsall.ui.emergency.PAEmergency
import com.example.petsall.ui.explore.PAExplore
import com.example.petsall.ui.files.PAFiles
import com.example.petsall.ui.home.PAHome
import com.example.petsall.ui.login.PALogin
import com.example.petsall.ui.navigation.Menu
import com.example.petsall.ui.navigation.Route
import com.example.petsall.ui.newPet.PANewPet
import com.example.petsall.ui.perfil.PAProfile
import com.example.petsall.ui.signup.PASignUp
import com.example.petsall.ui.theme.PetsAllTheme
import com.example.petsall.ui.theme.stItems
import com.example.petsall.ui.vaccination.PAVaccination
import com.example.petsall.ui.vet.PAVet
import com.example.petsall.ui.vetdetail.PAVetDetail
import com.example.petsall.utils.getObjectFromJson
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    var permissionRequestCount = 0
    private lateinit var requestMultiplePermissions: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                var selectMenu by rememberSaveable { mutableStateOf("Inicio") }

                val items = listOf(
                    Menu(Route.PAHome, Icons.Filled.Home, "Inicio"),
                    Menu(Route.PAVet, Icons.Filled.Favorite, "Veterinario"),
                    Menu(Route.PAEmergency, Icons.Filled.Warning, "Emergencia"),
                    Menu(
                        Route.PAExplore,
                        ImageVector.vectorResource(id = R.drawable.explore),
                        "Explora"
                    ),
                    Menu(Route.PAPerfil, Icons.Filled.Person, "Perfil"),
                )
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Scaffold(bottomBar = {

                        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.route in listOf(
                                Route.PAHome,
                                Route.PAEmergency,
                                Route.PAVet,
                                Route.PAPerfil,
                                Route.PAExplore
                            )
                        ) {
                            if (currentDestination?.route == Route.PAHome) {
                                selectMenu = "Inicio"
                            }
                            BottomNavigation {
                                items.forEach { item ->
                                    BottomNavigationItem(modifier = Modifier.background(Color.White),
                                        icon = {
                                            Icon(
                                                item.icon,
                                                contentDescription = null,
                                                tint = if (selectMenu == item.label) Color(
                                                    0xff84B1B8
                                                ) else Color(0xffDEDEDE)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = item.label,
                                                style = stItems.copy(
                                                    color = if (selectMenu == item.label) Color(
                                                        0xff84B1B8
                                                    ) else Color(
                                                        0xffDEDEDE
                                                    )
                                                ),
                                            )
                                        },
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
                                            selectMenu = item.label
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
                            composable(
                                "${Route.PABusinessList}/{nameList}",
                                arguments = listOf(navArgument("nameList") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                PABusinessList(
                                    nameListBusiness = backStackEntry.arguments?.getString(
                                        "nameList"
                                    ) ?: "", navController = navigationController
                                )
                            }
                            composable(Route.PAExplore) { PAExplore(navController = navigationController) }
                            composable(
                                "${Route.PAVaccinationCard}/{idUser}/{idPet}",
                                arguments = listOf(navArgument("idUser") {
                                    type = NavType.StringType
                                }, navArgument("idPet") {
                                    type = NavType.StringType
                                }
                                )
                            ) { backStackEntry ->
                                PAVaccination(
                                    idUser = backStackEntry.arguments?.getString("idUser") ?: "",
                                    idPet = backStackEntry.arguments?.getString("idPet") ?: "",
                                    navController = navigationController
                                )
                            }
                            composable(
                                "${Route.PAFiles}/{idPet}",
                                arguments = listOf(navArgument("idPet") {
                                    type = NavType.StringType
                                }
                                )
                            ) { backStackEntry ->
                                PAFiles(
                                    idPet = backStackEntry.arguments?.getString("idPet") ?: "",
                                    navController = navigationController
                                )
                            }
                            composable(Route.PANewPet) { PANewPet(navController = navigationController) }

                            composable(Route.PAPerfil) {
                                PAProfile {
                                    val intent =
                                        Intent(applicationContext, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            composable(
                                "${Route.PAChangePet}/{petSelect}",
                                arguments = listOf(navArgument("petSelect") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("petSelect")?.let {
                                    PAChangePet(
                                        petSelect = it,
                                        navController = navigationController,
                                    )
                                }
                            }
                            composable(
                                "${Route.PAVetDetail}/{dataVet}",
                                arguments = listOf(navArgument("dataVet") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                getObjectFromJson<VetData>(backStackEntry.arguments?.getString("dataVet"))?.let {
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



