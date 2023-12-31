package com.example.animalx.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animalx.presentation.ui.screen.detail.DetailScreen
import com.example.animalx.presentation.ui.screen.detail.DetailViewModel
import com.example.animalx.presentation.ui.screen.home.HomeScreen
import com.example.animalx.presentation.ui.screen.home.HomeViewModel

@Composable
fun AnimalXNavHost(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(
        key1 = true,
        block = {
            homeViewModel.getAnimals()
        })
    NavHost(
        navController = navController,
        startDestination = Home.route,
    ) {
        composable(route = Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onItemClickListener = { id ->
                    navController.navigate("${Detail.route}/$id")
                })
        }

        composable(
            route = Detail.routeWithArgs,
            arguments = Detail.arguments
        ) { navBackStackEntry ->
            val id =
                navBackStackEntry.arguments?.getLong(Detail.idArg)!!
            DetailScreen(
                id = id,
                viewModel = detailViewModel,
                backNavigation = {
                    navController.popBackStack()
                })
        }
    }
}
