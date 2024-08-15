package com.raouf.noteapp.ui


sealed class Screen (val route : String){
    data object Home : Screen(route = "home")
    data object Detail : Screen(route  = "detail")
}