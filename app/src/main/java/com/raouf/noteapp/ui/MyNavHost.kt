package com.raouf.noteapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.raouf.noteapp.NoteViewModel
import kotlinx.serialization.Serializable


@Composable
fun MyNavHost(
    navController: NavHostController,
    viewModel: NoteViewModel
){

    NavHost(
        navController = navController ,
        startDestination = Home
    ){
     composable<Home> {
         HomeScreen(event = viewModel::onEvent , state = viewModel.state.collectAsState() , navController )
     }

     composable<Detail> {
         val argn = it.toRoute<Detail>()
         DetailScreen(
             id = argn.id
         )
     }

    }

}
@Serializable
object Home

@Serializable
data class Detail(val id : Int)


