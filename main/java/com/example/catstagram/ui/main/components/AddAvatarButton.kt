package com.example.catstagram.ui.main.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.catstagram.R
import com.example.catstagram.data.local.Cat

@Composable
fun AddAvatarButton(
    saveImage: (Cat, Uri?) -> Unit,
    cat: Cat
) {
    val avatarPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> saveImage(cat, uri) }
    )

    IconButton(
        onClick = {
            avatarPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        modifier = Modifier
            .size(24.dp)
            .background(Color.White, CircleShape)
            .border(1.dp, Color.Gray, CircleShape),
        content = {
            Icon(
                painter = painterResource(R.drawable.camera),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
        }
    )
}