package com.example.catstagram.ui.main.components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R
import com.example.catstagram.data.local.Cat
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun CatCard(
    saveImage: (Cat, Uri?) -> Unit,
    cat: Cat
) {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RectangleShape,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.wallpapers),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProfilePicture(
                    saveImage = saveImage,
                    cat = cat
                )
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CatInfo(cat)
            }
        }
    }
}

@Composable
fun CatInfo(
    cat: Cat
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = if (cat.gender == 'F')
                    painterResource(R.drawable.female_icon) else painterResource(R.drawable.male_icon),
                modifier = Modifier
                    .size(36.dp)
                    .padding(8.dp),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Text(
                text = cat.name,
                fontSize = 28.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = cat.birthDate.toString(),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CatCardPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CatCard(
            saveImage = { cat, uri -> },
            cat = Cat(
                name = "Shady",
                gender = 'F',
                birthDate = LocalDate.now()
            )
        )
    }
}