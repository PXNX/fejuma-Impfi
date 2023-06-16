package de.fejuma.impfi.presentation.game.component

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
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


    Box(
        modifier = Modifier


            .padding(2.dp)
            .size(48.dp)
            .clip(CircleShape)
            .combinedClickable(
                enabled = tile.coverMode != TileCoverMode.UNCOVERED,
                onClick = {
                    onTileSelected(tile.x, tile.y)

                },
                onLongClick = {
                    onTileSelectedSecondary(tile.x, tile.y)
                },

                )


            .then(


                when (tile.coverMode) {
                    TileCoverMode.UNCOVERED -> when (tile) {
                        is Tile.Adjacent -> Modifier
                            .background(Color.Transparent)
                            .border(2.dp, Color.LightGray, CircleShape)

                        is Tile.Empty -> Modifier
                            .background(Color.Transparent)
                            .border(2.dp, Color.DarkGray, CircleShape)

                        else -> Modifier.background(Color.Red)
                    }

                    TileCoverMode.FLAGGED -> Modifier
                        .background(Color(0xFF76FF03))
                        .border(
                            2.dp, linearGradient(
                                colors = listOf(
                                    Color(0xAAFFFFFF),
                                    Color(0xAACCCCCC),
                                    Color(0xAA444444)
                                ),

                                ), CircleShape
                        )
                        .padding(6.dp)

                    TileCoverMode.QUESTIONED -> Modifier
                        .background(Color.Yellow)
                        .border(
                            2.dp, linearGradient(
                                colors = listOf(
                                    Color(0xAAFFFFFF),
                                    Color(0xAACCCCCC),
                                    Color(0xAA444444)
                                ),

                                ), CircleShape
                        )
                        .padding(6.dp)

                    else -> Modifier
                        .background(
                            MaterialTheme.colorScheme.secondary
                        )
                        .border(
                            2.dp, linearGradient(
                                colors = listOf(
                                    Color(0xAAFFFFFF),
                                    Color(0xAACCCCCC),
                                    Color(0xAA444444)
                                ),

                                ), CircleShape
                        )
                }


            )


        //       .indication(interactionSource, LocalIndication.current)


        // .clip(RoundedCornerShape(4.dp))
        //    .neumorphic(neuShape =  Pressed.Rounded(4.dp), strokeWidth = 2.dp),
        //    .background(if (tile.coverMode == TileCoverMode.UNCOVERED) Color.Transparent else Color.DarkGray),
        ,
        contentAlignment = Alignment.Center

    ) {


        when (tile.coverMode) {
            TileCoverMode.COVERED -> {

            }

            TileCoverMode.FLAGGED -> Icon(
                painter = painterResource(id = R.drawable.needle),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                tint = Color.White
            )

            TileCoverMode.QUESTIONED -> Icon(
                painter = painterResource(id = R.drawable.help),
                contentDescription = "Question",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Black
            )

            TileCoverMode.UNCOVERED -> when (tile) {


                is Tile.Adjacent -> {
                    val riskColor = when (tile.risk) {
                        1 -> Color.Blue
                        2 -> Color.Green
                        3 -> Color.Red
                        4 -> Color.Magenta
                        else -> Color.Yellow

                    }

                    Text(
                        "${tile.risk}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = riskColor
                    )
                }

                is Tile.Empty -> {

                }

                is Tile.Bomb -> when (tile.userSelection) {
                    true -> {
                        Image(
                            painterResource(id = R.mipmap.virus),
                            contentDescription = "Virus",
                            Modifier.fillMaxSize()
                        )

                    }

                    false -> Image(
                        painterResource(id = R.mipmap.virus),
                        contentDescription = "Virus",
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
                Tile.Bomb(TileCoverMode.UNCOVERED, 0, 0, false),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Empty(TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(1, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(2, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(3, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(2, TileCoverMode.COVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(2, TileCoverMode.FLAGGED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                Tile.Adjacent(2, TileCoverMode.QUESTIONED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })


        }
    }
}


private const val CONTENT_TRANSFORM_ANIM_DURATION: Int = 300

@ExperimentalAnimationApi
private fun getContentTransformAnim(): ContentTransform {

    val enterAnim = scaleIn(
        animationSpec = tween(
            durationMillis = CONTENT_TRANSFORM_ANIM_DURATION,
            delayMillis = CONTENT_TRANSFORM_ANIM_DURATION / 2
        )
    )

    val exitAnim = scaleOut(
        animationSpec = tween(
            durationMillis = CONTENT_TRANSFORM_ANIM_DURATION,
            delayMillis = 0,
        )
    )

    return ContentTransform(enterAnim, exitAnim)
}
