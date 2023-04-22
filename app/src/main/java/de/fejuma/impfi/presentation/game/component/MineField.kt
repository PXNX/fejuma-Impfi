package de.fejuma.impfi.presentation.game.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.R
import de.fejuma.impfi.presentation.game.game.Tile
import de.fejuma.impfi.presentation.game.game.TileCoverMode


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MineField(
    tile: Tile,
    x: Int,
    y: Int,
    onTileSelected: (column: Int, row: Int) -> Unit,
    onTileSelectedSecondary: (column: Int, row: Int) -> Unit,
) {


    // val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(16.dp)
            //       .indication(interactionSource, LocalIndication.current)

            .combinedClickable(
                enabled = tile.coverMode != TileCoverMode.UNCOVERED,
                onClick = {
                    onTileSelected(x, y)

                },
                onLongClick = {
                    onTileSelectedSecondary(x, y)
                },
            )
            // .clip(RoundedCornerShape(4.dp))
            .background(if (tile.coverMode == TileCoverMode.UNCOVERED) Color.Transparent else Color.DarkGray),

        contentAlignment = Alignment.Center

    ) {
        when (tile.coverMode) {
            TileCoverMode.COVERED -> {

            }

            TileCoverMode.FLAGGED -> Icon(
                painter = painterResource(id = R.drawable.needle),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Green
            )

            TileCoverMode.UNCOVERED -> when (tile) {
                is Tile.Adjacent -> Text(
                    "${tile.risk}",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                is Tile.Empty -> {

                }

                is Tile.Bomb -> when (tile.userSelection) {
                    true -> Icon(
                        painter = painterResource(id = R.drawable.virus_outline),
                        contentDescription = "Virus",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Red
                    )

                    false -> Icon(
                        painter = painterResource(id = R.drawable.virus_outline),
                        contentDescription = "Virus",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Yellow
                    )
                }
            }
        }
    }


}