package com.example.catstagram.ui.registration.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R

@Composable
fun NavigationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9370DB),
            contentColor = Color.Black
        ),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(R.string.nav_continue),
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}
