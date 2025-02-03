package com.example.catstagram.ui.main.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.catstagram.R
import com.example.catstagram.data.local.Cat
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.now
import java.io.File

@Composable
fun ProfilePicture(
    saveImage: (Cat, Uri?) -> Unit,
    cat: Cat
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(128.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        AsyncImage(
            model = cat.avatarUri?.let { fileName ->
                val file = File(context.filesDir, fileName)
                if (file.exists()) Uri.fromFile(file) else R.drawable.cat_ava
            } ?: R.drawable.cat_ava,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop,

            )
        AddAvatarButton(
            saveImage = saveImage,
            cat = cat
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProfilePicturePreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ProfilePicture({ cat, uri -> }, Cat(name = "", birthDate = LocalDate.now(), gender = 'c'))

    }
}

