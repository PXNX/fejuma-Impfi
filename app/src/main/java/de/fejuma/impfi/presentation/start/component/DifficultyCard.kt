package de.fejuma.impfi.presentation.start.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.model.Difficulty
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.ui.MinesweeperTheme
import de.fejuma.impfi.ui.darkGray


@Composable
fun RowScope.DifficultyCard(
    difficulty: Difficulty,
    isActive: Boolean,
    setActive: () -> Unit
) {

    // Determine the colors for stroke, text, and background based on the active state
    val strokeColor: Color?
    val textColor: Color?
    // Smooth switching between colors, as this is a larger surface and just turning it on an off
    // feels bad and is very noticeable
    val backgroundColor: Color by animateColorAsState(
        if (isActive)
            MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(300, easing = LinearEasing), label = "backgroundColor"
    )

    if (isActive) {
        strokeColor = Color.Transparent
        textColor = Color.White
    } else {
        strokeColor = darkGray
        textColor = darkGray
    }

    // Define the layout and appearance of the DifficultyCard
    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(1f, false)
        .clip(RoundedCornerShape(8.dp))
        .clickable { setActive() }
        .background(backgroundColor)
        .border(1.dp, strokeColor, RoundedCornerShape(8.dp))
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Display the difficulty name
        Text(
            text = stringResource(id = difficulty.nameResource),
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        // Display the dimensions of the difficulty
        Text(
            // Interpolating strings
            text = "${difficulty.height} × ${difficulty.width} ${stringResource(id = R.string.field_amount)}",
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontSize = 10.sp,
        )

        // Display the number of mines in the difficulty
        Text(
            text = "${difficulty.mines} ${stringResource(id = R.string.mines_amount)}",
            modifier = Modifier.weight(1f, fill = false),
            color = textColor,
            fontSize = 10.sp,
        )


    }

}

// Displaying the DifficultyCard in different states and with different device settings
@DefaultPreviews
@Composable
private fun DifficultyCardPreview() = MinesweeperTheme {

    Row {
        DifficultyCard(difficulties[DifficultyLevel.EASY]!!, true, {})
        DifficultyCard(difficulties[DifficultyLevel.NORMAL]!!, false, {})
    }
}