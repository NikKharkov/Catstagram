package com.example.catstagram.ui.registration.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catstagram.R
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView

@Composable
fun ChooseDateTextField(
    birthDate: LocalDate,
    onSetBirthDate: (LocalDate) -> Unit
) {
    var isShowingDialog by remember { mutableStateOf(false) }
    Column {
        Text(
            text = stringResource(R.string.birthday_date),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Gray, Color.Black)
                    )
                )
                .clickable { isShowingDialog = true }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = birthDate.toString(),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = null
                )
            }
        }

        WheelDatePickerView(
            height = 200.dp,
            showDatePicker = isShowingDialog,
            title = "",
            startDate = birthDate,
            doneLabel = stringResource(R.string.done),
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDateChangeListener = { date ->
                onSetBirthDate(date)
            },
            onDoneClick = { date ->
                onSetBirthDate(date)
                isShowingDialog = false
            }
        )
    }
}