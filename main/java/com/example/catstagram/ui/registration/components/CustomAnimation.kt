package com.example.catstagram.ui.registration.components

import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@Composable
fun GifAnimation(
    @DrawableRes drawable: Int,
    modifier: Modifier
) {
    val gifEnabledLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    AsyncImage(
        model = drawable,
        imageLoader = gifEnabledLoader,
        modifier = modifier,
        contentDescription = null
    )
}