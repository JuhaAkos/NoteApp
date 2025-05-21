package com.example.baseapp3.notes_features.present.notes

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baseapp3.R

import com.example.baseapp3.notes_features.present.notes.components.NoteItem
import com.example.baseapp3.notes_features.present.notes.components.OrderSection
import com.example.baseapp3.notes_features.present.util.Screen


@ExperimentalAnimationApi
@Composable
fun NotesScreen (
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value //state reference
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.AddEditNoteScreen.route) //navigation call
            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
        }
    },
    scaffoldState = scaffoldState
    ) {
        innerPadding -> //afterfix
        Column(
            Modifier.fillMaxSize()
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondary)
                .padding(bottom = 10.dp)
                .padding(start = 24.dp)
                .padding(end = 24.dp)
                .padding(top = 30.dp)
                ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Notes app",
                style = MaterialTheme.typography.h4
            )
            IconButton(
                onClick = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    modifier = Modifier
                        .size(56.dp)
                        //.padding(top = 14.dp)
                        ,
                    contentDescription = "Sort"
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding) //afterfix
                .padding(bottom = 24.dp)
                .padding(start = 24.dp)
                .padding(end = 24.dp)
                .padding(top = 10.dp)

                .paint(
                    // Replace with your image id

                    painterResource(id = R.drawable.sealicon),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.secondary),
                    contentScale = ContentScale.Fit,
                    //alpha = 0.5F,
                ),


        ) {
            /*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    //.background(MaterialTheme.colors.secondary)
                    ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notes app",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        modifier = Modifier
                            .size(56.dp)
                            .padding(top = 14.dp),
                        contentDescription = "Sort"
                    )
                }
            }
            */
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(vertical = 16.dp)
                        ,
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    }
}