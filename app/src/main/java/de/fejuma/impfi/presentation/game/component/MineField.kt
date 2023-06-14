package de.fejuma.impfi.presentation.game.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.presentation.game.game.Tile
import de.fejuma.impfi.presentation.game.game.TileCoverMode
import de.fejuma.impfi.ui.MinesweeperTheme


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MineField(
    tile: Tile,

    onTileSelected: (column: Int, row: Int) -> Unit,
    onTileSelectedSecondary: (column: Int, row: Int) -> Unit,
) {


    // val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(48.dp)


            .then(
                if (tile.coverMode == TileCoverMode.UNCOVERED)

                    if (tile is Tile.Empty || tile is Tile.Adjacent)
                        Modifier
                            .background(Color.Transparent)
                            .border(1.dp, Color.DarkGray)
                    else
                        Modifier.background(Color.Red)
                else if (tile.coverMode == TileCoverMode.FLAGGED)
                    Modifier.background(Color.Yellow)
                else if (tile.coverMode == TileCoverMode.QUESTIONED)
                    Modifier.background(Color.Green)
                else Modifier


            )


            //       .indication(interactionSource, LocalIndication.current)

            .combinedClickable(
                enabled = tile.coverMode != TileCoverMode.UNCOVERED,
                onClick = {
                    onTileSelected(tile.x, tile.y)

                },
                onLongClick = {
                    onTileSelectedSecondary(tile.x, tile.y)
                },
            )
        // .clip(RoundedCornerShape(4.dp))
        //    .neumorphic(neuShape =  Pressed.Rounded(4.dp), strokeWidth = 2.dp),
        //    .background(if (tile.coverMode == TileCoverMode.UNCOVERED) Color.Transparent else Color.DarkGray),
        ,
        contentAlignment = Alignment.Center

    ) {
        if (tile.coverMode != TileCoverMode.UNCOVERED) {
            Image(
                painter = painterResource(id = R.mipmap.box),
                contentDescription = "",
                Modifier.fillMaxSize()
            )
        }

        when (tile.coverMode) {
            TileCoverMode.COVERED -> {

            }

            TileCoverMode.FLAGGED -> Icon(
                painter = painterResource(id = R.drawable.needle),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Green
            )

            TileCoverMode.QUESTIONED -> Icon(
                painter = painterResource(id = R.drawable.help),
                contentDescription = "Question",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Yellow
            )

            TileCoverMode.UNCOVERED -> when (tile) {


                is Tile.Adjacent -> {
                    val riskColor = when (tile.risk) {
                        1 -> Color.Blue
                        2 -> Color.Green
                        3 -> Color.Red
                        4 -> Color.Magenta
                        else -> Color.White

                    }

                    Text(
                        "${tile.risk}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = riskColor
                    )
                }

                is Tile.Empty -> {

                }

                is Tile.Bomb -> when (tile.userSelection) {
                    true -> Icon(
                        painter = painterResource(id = R.drawable.virus_outline),
                        contentDescription = "Virus",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Red
                    )

                    /*   false -> Icon(
                           painter = painterResource(id = R.drawable.virus_outline),
                           contentDescription = "Virus",
                           modifier = Modifier.fillMaxSize(),
                           tint = Color.Yellow
                       )

                     */
                    false -> Image(
                        painterResource(id = R.mipmap.ogre),
                        contentDescription = null,
                        Modifier.fillMaxSize()
                    )
                }
            }
        }
    }


}

@DefaultPreviews
@Composable
fun MineFieldPreview() {
    MinesweeperTheme {

        Row {


            MineField(
                tile = Tile.Bomb(TileCoverMode.UNCOVERED, 0, 0, false),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(2.dp, Color.White, RoundedCornerShape(4.dp))

                    .background(Color.DarkGray),

                contentAlignment = Alignment.Center

            ) {

            }

            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .size(20.dp)


                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .background(Color.DarkGray),

                contentAlignment = Alignment.Center

            ) {

            }


        }
    }
}







