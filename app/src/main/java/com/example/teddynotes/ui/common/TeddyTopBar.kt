package com.example.teddynotes.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teddynotes.ui.theme.BearDeep
import com.example.teddynotes.ui.theme.Nunito
import com.example.teddynotes.ui.theme.PrimaryTextBrown
import com.example.teddynotes.ui.theme.SoftWhite

@Composable
fun TeddyTopBar(onBack: () -> Unit, showSearch: Boolean = false, onSearch: () -> Unit = {},
                isSearchActive: Boolean = false,
                searchQuery: String = "",
                onSearchQueryChange: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = BearDeep,
                contentDescription = "Back Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        if (showSearch && isSearchActive) {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(isSearchActive) {
                if (isSearchActive) focusRequester.requestFocus()
            }
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                textStyle = TextStyle(
                    fontFamily = Nunito,
                    fontSize = 16.sp,
                    color = PrimaryTextBrown
                ),
                modifier = Modifier.weight(1f).focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SoftWhite)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text("Search by title", color = PrimaryTextBrown.copy(0.4f), fontFamily = Nunito)
                        }
                        innerTextField()
                    }
                }
            )
        }
        if(showSearch){
            IconButton(onClick = onSearch) {
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