package com.example.animalx.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


interface AnimalXDestination {
    val route: String
}

object Home : AnimalXDestination {
    override val route = "home"
}

object Detail : AnimalXDestination {
    override val route = "detail"

    const val idArg = "id"

    val routeWithArgs = "$route/{$idArg}"

    val arguments = listOf(navArgument(idArg) { type = NavType.LongType })
}
