package de.fejuma.impfi.presentation.game.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val REVEAL_DURATION: Long = 250L


@Composable
fun LaunchWithCircularReveal(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    // We want to draw a little circle whenever we enter a new game, so only once.
    val isFirstLaunch = rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        delay(REVEAL_DURATION * 2)
        isFirstLaunch.value = false
    }

    Box(
        modifier = modifier,
    ) {

        content()

        if (isFirstLaunch.value) {
            CircularRevealLayout(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
private fun CircularRevealLayout(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colorScheme.background,
    strokeColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    val height = with(LocalConfiguration.current) {
        with(LocalDensity.current) { screenHeightDp.dp.toPx() }
    }
    val maxRadiusPx = height + 164

    var targetRadius by remember { mutableFloatStateOf(maxRadiusPx) }
    val radius = animateFloatAsState(
        targetValue = targetRadius,
        animationSpec = tween(
            durationMillis = REVEAL_DURATION.toInt(),
        ),
        label = "",
    )

    // Here the inner circle and the bright stroke around it are created
    Spacer(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val drawOffset = Offset(size.width / 2, size.height / 2)

                // Circle
                drawCircle(
                    color = circleColor,
                    radius = radius.value,
                    center = drawOffset,
                )
                // Circle stroke
                drawCircle(
                    color = strokeColor,
                    radius = radius.value,
                    style = Stroke(width = 8f),
                    center = drawOffset,
                )
            },
    )

    LaunchedEffect(false) {
        targetRadius = 0f
    }
}