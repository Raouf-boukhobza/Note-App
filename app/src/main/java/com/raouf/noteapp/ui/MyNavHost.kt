package com.raouf.noteapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.raouf.noteapp.ViewModel.homeScreen.NoteViewModel
import com.raouf.noteapp.ViewModel.detailScreen.DetailViewModel
import kotlinx.serialization.Serializable


@Composable
fun MyNavHost(
    navController: NavHostController,
){

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            val homeViewModel : NoteViewModel = hiltViewModel(it)
            HomeScreen(
                onEvent = homeViewModel::onEvent,
                state = homeViewModel.state.collectAsState(),
                navController = navController )
        }

        composable<Detail> {
            val detailViewModel : DetailViewModel = hiltViewModel(it)

            val args = it.toRoute<Detail>()

            DetailScreen(
                id =args.id ,
                detailViewModel.state.collectAsState(),
                detailViewModel::onEvent,
                navController
            )
        }
    }


}


@Serializable
object Home

@Serializable
data class Detail(val id : String?)




