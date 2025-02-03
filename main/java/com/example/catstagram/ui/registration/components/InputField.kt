package com.example.catstagram.ui.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {

    Column {
        Text(
            text = stringResource(R.string.name),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        OutlinedTextField(
            value = value,
            shape = RectangleShape,
            singleLine = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.paw),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null
                )
            },
            isError = isError,
            supportingText = {
                if (isError) (Text(text = stringResource(R.string.cat_needs_name)))
            },
            label = {
                Text(
                    text = stringResource(R.string.enter_name),
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        )
    }
}
