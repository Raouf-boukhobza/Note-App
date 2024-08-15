package com.raouf.noteapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun DetailScreen(
    id : Int
){
    Text(text = "hello $id")
}