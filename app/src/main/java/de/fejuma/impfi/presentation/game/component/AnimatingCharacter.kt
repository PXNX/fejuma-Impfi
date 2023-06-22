package de.fejuma.impfi.presentation.game.component


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatingCharacter(
    character: Char,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    color: Color = MaterialTheme.colorScheme.primary,
) {

    // Here we define how a composable should transition to another state visually: We want whatever
    // character we get to move up and fade out while the new character should also move up - but
    // starting from the bottom of this Composable - and fade in
    AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            (slideInVertically { height -> height } + fadeIn())
                .togetherWith(slideOutVertically { height -> -height } + fadeOut())
        },
        targetState = character, label = "",
    ) { targetState ->
        Text(
            text = targetState.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        )
    }
}