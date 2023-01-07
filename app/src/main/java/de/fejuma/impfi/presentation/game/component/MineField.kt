package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.R
import de.fejuma.impfi.presentation.game.MineFieldState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MineField(
    state: MineFieldState,
    setState: () -> Unit,
    setFlag: () -> Unit
) {


    Surface(
        color = Color.LightGray,
        border = BorderStroke(1.dp, Color.Yellow),
        modifier = Modifier
            .size(40.dp)
            .combinedClickable(
                onClick = {
                    setState()

                },
                onLongClick = {
                    setFlag()
                },
            )
    ) {
        when (state) {
            MineFieldState.FLAG -> Icon(
                painter = painterResource(id = R.drawable.needle),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Green
            )

            MineFieldState.VIRUS -> Icon(
                painter = painterResource(id = R.drawable.virus_outline),
                contentDescription = "Virus",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Red
            )

            else -> {

            }
        }


    }

}