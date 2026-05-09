package com.example.teddynotes.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teddynotes.ui.theme.BearDeep

@Composable
fun TeddyTopBar(onBack: () -> Unit, showSearch: Boolean = false, onSearch: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {onBack}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = BearDeep,
                contentDescription = "Back Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        if(showSearch){
            IconButton(onClick = {onSearch}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = BearDeep,
                    contentDescription = "Search Icon",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }

}