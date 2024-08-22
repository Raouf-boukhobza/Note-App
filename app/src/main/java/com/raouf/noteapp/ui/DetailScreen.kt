package com.raouf.noteapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.raouf.noteapp.Data.Local.NoteType
import com.raouf.noteapp.ViewModel.detailScreen.DetailEvent
import com.raouf.noteapp.ViewModel.detailScreen.DetailState
import com.raouf.noteapp.ui.theme.deepPurple
import com.raouf.noteapp.ui.theme.green
import com.raouf.noteapp.ui.theme.lightBlue
import com.raouf.noteapp.ui.theme.orange
import com.raouf.noteapp.ui.theme.pink


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String?,
    state: State<DetailState>,
    onEvent: (DetailEvent) -> Unit,
    navController: NavHostController
){

    id?.let {
        onEvent(DetailEvent.SelectNote(id.toInt()))
    }

    Scaffold( modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back to home screen" )
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = {
                        if (id == null) onEvent(DetailEvent.SaveNote)
                        else onEvent(DetailEvent.SavaUpdate(id.toInt()))
                        navController.popBackStack()
                    },
                       ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "save note",
                            modifier = Modifier.size(30.dp))
                    }
                }
            )
        },
        containerColor = Color.Black
    ){paddingValues ->

      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(paddingValues)
              .padding(8.dp) ,
          verticalArrangement = Arrangement.spacedBy(14.dp)){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                NoteType.entries.forEach { type ->
                    TypeButton(type = type,
                        onButtonClick = {event ->
                        onEvent(event)
                                        } ,
                        state = state
                    )
                }
            }
            OutlinedTextField(
                value = state.value.title , onValueChange = {
                    onEvent(DetailEvent.AddTitle(it))
                },
                textStyle = TextStyle(
                    fontSize = 32.sp
                ),
              shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(

                   focusedBorderColor = Color.Black,

                    unfocusedBorderColor = Color.Black
                ),
               placeholder = {
                    Text(
                        text = "TAB TO ADD THE TITLE",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            )
            OutlinedTextField(
                value = state.value.description,
                onValueChange ={
                    onEvent(DetailEvent.AddDescription(it))
                },
                textStyle = TextStyle(
                    fontSize = 22.sp
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ) ,
                placeholder = {
                    Text(
                    text = "TAB TO ADD THE DESCRIPTION",
                    fontSize = 16.sp,
                    color = Color.Gray
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){
             NoteColors(
                 state = state,
                 onColorchange = { event ->
                     onEvent(event)
                 }
             )
            }
      }
    }
}

@Composable
fun NoteColors(
    state: State<DetailState>,
    onColorchange : (DetailEvent) -> Unit
){

    val colors = listOf(pink , green , orange , lightBlue , deepPurple)
    colors.forEach{ color ->
        Box(modifier = Modifier
            .clip(CircleShape)
            .size(50.dp)
            .background(color = color)
            .selectable(
                selected = color == state.value.color,
                onClick = { onColorchange(DetailEvent.AddColor(color)) }
            )
            .border(
                width = if (color == state.value.color) 2.5.dp
                else 0.dp,
                color = Color.White,
                shape = CircleShape,
            )
        )
    }
}

@Composable
private fun TypeButton(
    type : NoteType,
    onButtonClick : (DetailEvent) -> Unit,
    state: State<DetailState>
){
    var buttoncolor =  Color.Transparent
    var border: BorderStroke? = BorderStroke(1.dp , Color.Gray)
    var textColor= Color.Gray

    if (type == state.value.type){
        buttoncolor =  Color.White
        border = null
        textColor= state.value.color
    }
    Button(
        onClick = { onButtonClick(DetailEvent.AddType(type)) },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            buttoncolor
        ),
        border = border,
        modifier = Modifier
            .selectable(
                selected = type == state.value.type,
                onClick = {onButtonClick(DetailEvent.AddType(type))}
            )
    ){
        Text(
            text = type.name,
            fontSize = 14.sp,
            color = textColor,
        )
    }
}


