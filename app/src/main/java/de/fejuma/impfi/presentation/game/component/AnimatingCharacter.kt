package de.fejuma.impfi.presentation.game.component


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatingCharacter(
    modifier: Modifier = Modifier,
    character: Char,
) {

    AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut()
        },
        targetState = character, label = "",
    ) { targetState ->

        Text(
            text = targetState.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        )
    }
}