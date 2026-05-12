package com.example.teddynotes.ui.notes_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teddynotes.model.Note
import com.example.teddynotes.navigation.NavRoutes
import com.example.teddynotes.ui.common.TeddyTopBar
import com.example.teddynotes.ui.theme.BackgroundGreen
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite
import com.example.teddynotes.viewmodel.NoteViewModel

@Composable
fun NotesListScreen(navController: NavController, noteViewModel: NoteViewModel) {

    val notes by noteViewModel.allNotes.collectAsState()
    var search by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val filteredNotes = if (search.isBlank()) notes else notes.filter {
        it.title.contains(
            search,
            ignoreCase = true
        )
    }

    Scaffold(containerColor = BackgroundGreen) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TeddyTopBar(
                onBack = { navController.popBackStack() },
                showSearch = true,
                onSearch = {
                    isSearchActive = !isSearchActive
                    if (!isSearchActive) search = ""
                },
                isSearchActive = isSearchActive,
                searchQuery = search,
                onSearchQueryChange = {search = it}
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 20.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(SoftWhite.copy(alpha = 0.65f))
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredNotes) { note ->
                        NoteCard(
                            note = note,
                            onDelete = { noteViewModel.deleteNote(note) },
                            onClick = { navController.navigate(NavRoutes.NoteScreen(note.date)) }
                        )
                    }
                }


            }
        }
    }
}

@Composable
fun NoteCard(note: Note, onDelete: () -> Unit, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(note.mood.color)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.title,
                fontFamily = Nunito,
                fontSize = 16.sp,
                color = PrimaryTextBrown,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = note.date, fontFamily = Nunito, fontSize = 12.sp, color = PrimaryTextBrown
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = PrimaryTextBrown
                )
            }

        }
    }
}

