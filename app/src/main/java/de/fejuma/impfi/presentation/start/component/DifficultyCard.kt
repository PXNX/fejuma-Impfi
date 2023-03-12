package de.fejuma.impfi.presentation.start.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.R
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.ui.darkGray
import de.fejuma.impfi.ui.lightGray


@Composable
fun RowScope.DifficultyCard(
    difficulty: Difficulty,
    isActive: Boolean,
    setActive: () -> Unit
) {

    val backgroundColor: Color?
    val strokeColor: Color?
    val textColor: Color?

    if (isActive) {
        backgroundColor = difficulty.difficultyColor
        strokeColor = colorResource(id = R.color.lightGreen)
        textColor = colorResource(id = R.color.black)
    } else {
        backgroundColor = lightGray
        strokeColor = darkGray
        textColor = darkGray
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(1f, false)
        .clip(RoundedCornerShape(6.dp))
        /* .indication(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true)
        ) */
        .clickable { setActive() }


        .background(backgroundColor)
        .border(1.dp, strokeColor, RoundedCornerShape(6.dp))


        .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = difficulty.name,
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        Text(
            text = "${difficulty.fieldAmount} ${stringResource(id = R.string.field_amount)}",
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontSize = 10.sp,
        )

        Text(
            text = "${difficulty.minesAmount} ${stringResource(id = R.string.mines_amount)}",
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontSize = 10.sp,
        )


    }

}