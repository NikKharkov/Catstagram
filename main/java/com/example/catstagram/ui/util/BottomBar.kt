package com.example.catstagram.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R

@Composable
fun BottomBar(
    navigateToMainScreen: () -> Unit,
    navigateToGallery: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    navigateToVideosScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
) {
    NavigationBar {
        // To main screen
        NavigationBarItem(
            selected = false,
            onClick = navigateToMainScreen,
            icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
            label = { Text(stringResource(R.string.Home)) }
        )
        // To gallery
        NavigationBarItem(
            selected = false,
            onClick = navigateToGallery,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.gallery_icon),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null
                )
            },
            label = { Text(stringResource(R.string.gallery)) }
        )
        // Registration
        NavigationBarItem(
            selected = false,
            onClick = navigateToRegistrationScreen,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                )
            },
            label = { Text(stringResource(R.string.add)) }
        )
        // To videos
        NavigationBarItem(
            selected = false,
            onClick = navigateToVideosScreen,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.numerator),
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            },
            label = { Text(stringResource(R.string.video)) }
        )
        // To settings
        NavigationBarItem(
            selected = false,
            onClick = navigateToSettingsScreen,
            icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
            label = {
                Text(
                    text = stringResource(R.string.settings),
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(
    showBackground = true
)
@Composable
fun BottomAppBarPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                {}, {}, {}, {}, {}
            )
        }
    ) {

    }
}