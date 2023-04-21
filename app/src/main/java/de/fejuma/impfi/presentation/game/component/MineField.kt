package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.R
import de.fejuma.impfi.presentation.game.MineFieldState
import de.fejuma.impfi.ui.darkGray


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MineField(
    state: MineFieldState,
    nearbyMines: Int,

    setState: () -> Unit,
    setFlag: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }


    Surface(
        color = Color.LightGray,
        border = BorderStroke(1.dp, darkGray),
        modifier = Modifier
            .size(50.dp)
            .indication(interactionSource, LocalIndication.current)

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

            MineFieldState.NUMBER -> Text("$nearbyMines")

            else -> {

            }
        }


    }

}