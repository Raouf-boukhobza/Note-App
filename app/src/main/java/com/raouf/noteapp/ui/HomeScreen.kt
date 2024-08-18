package com.raouf.noteapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.raouf.noteapp.Data.Local.Sort
import com.raouf.noteapp.ViewModel.NoteEvent
import com.raouf.noteapp.ViewModel.NoteState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEvent : (NoteEvent) -> Unit,
    state: State<NoteState>,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Notes",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onEvent(NoteEvent.openDetail)
                    navController.navigate(
                    Detail(
                        id = null
                    )
                ) },
                containerColor = Color.White,

                ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add a new note"
                )
            }
        },
        containerColor = Color.Black
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ){
                Sort.entries.forEach { sortType ->

                    SortButton(
                        sortType = sortType,
                        onButtonClick = { noteEvent ->
                            onEvent(noteEvent)
                        },
                        state = state
                    )

                }

            }
            
            Spacer(modifier = Modifier.height(25.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = rememberLazyGridState()
            ){
                val list = state.value.noteList

                    items(list){ note ->
                        NotesView(
                            title = note.title,
                            description = note.description,
                            color = Color(note.color),
                            onNoteClick = {
                                onEvent(NoteEvent.openDetail)
                                navController.navigate(
                                  Detail(
                                      id = note.id.toString()
                                  )
                              )
                            },
                            onLongClick = {
                                onEvent(NoteEvent.OpenDialog)
                            }
                        )
                        if (state.value.isDeletingNote){
                            DeleteDialog(Cancel = { onEvent(NoteEvent.CloseDialog) }) {
                                onEvent(NoteEvent.DeleteNote(note))
                            }
                        }
                    }
            }


        }
    }
}



@Composable
private fun SortButton(
    sortType: Sort,
    onButtonClick : (NoteEvent) -> Unit,
    state: State<NoteState>
){
    var buttoncolor =  Color.Transparent
    var border: BorderStroke? = BorderStroke(1.dp , Color.Gray)
    var noteNumber = ""
    var textColor= Color.Gray

    if (sortType == state.value.Sort){
         buttoncolor =  Color.White
         border = null
         noteNumber = "(${state.value.noteList.size})"
         textColor= Color.Black
    }
        Button(
            onClick = { onButtonClick(NoteEvent.SortType(sortType)) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                buttoncolor
            ),
            border = border,
            modifier = Modifier
                .selectable(
                    selected = sortType == state.value.Sort,
                    onClick = {onButtonClick(NoteEvent.SortType(sortType))}
                )
        ){
            Text(
                text = sortType.name + noteNumber,
                fontSize = 16.sp,
                color = textColor,
                fontWeight = FontWeight.W700
            )
        }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NotesView(
    title : String,
    description: String,
    color: Color,
    onNoteClick : () -> Unit,
    onLongClick : () -> Unit
){
    Surface(
        modifier = Modifier
            .width(170.dp)
            .combinedClickable(
                onClick = {
                    onNoteClick()
                },
                onLongClick = {
                    onLongClick()
                }
            ),
        color = color,
        shape = RoundedCornerShape(18.dp)
    ){
         Column (
             horizontalAlignment = Alignment.Start,
             modifier = Modifier.padding(16.dp),
             verticalArrangement = Arrangement.spacedBy(12.dp)
         ){
           Text(
               text = title,
               fontSize = 22.sp,
               fontWeight = FontWeight.Bold
           )
             Text(
                 text = description,
                 fontSize = 18.sp,
                 maxLines = 3
             )
         }
    }
}


@Composable
fun DeleteDialog(
    Cancel : () -> Unit,
    Confirme: () -> Unit
){
    AlertDialog(
        onDismissRequest = {Cancel() },
        dismissButton = {
            Button(onClick = { Cancel()}) {
                Text(text = "Cancel" ,
                    color = Color.White
                )
            }
        },
        confirmButton = { Button(onClick = { Confirme() }) {
            Text(text = "Delete" ,
                fontWeight = FontWeight.Medium,
                color = Color.Red
            )
        } },
        title = {
            Text(
                text = "Delete this Note",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        },
        text = {
            Text(
                text = "Are You Sure you went to delete this note ?",
                fontSize = 16.sp,
                color = Color.Gray
            )
        },
        containerColor = Color.DarkGray,
    )
}





