package com.example.catstagram.ui.main

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.catstagram.data.local.Cat
import com.example.catstagram.ui.main.components.CatCard

@Composable
fun MainScreen(
    saveImage: (Cat, Uri?) -> Unit,
    contentPaddingValues: PaddingValues,
    cats: List<Cat>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPaddingValues)
    ) {
        items(cats)
        { cat ->
            CatCard(
                saveImage = { cat, uri ->
                    saveImage(cat, uri)
                },
                cat = cat
            )
        }
    }
}
