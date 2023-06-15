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


    // val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier


            .padding(2.dp)
            .size(48.dp)


            .clip(CircleShape)


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
                        .background(Color(0xFF4CAF50))
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
                        Icon(
                            painter = painterResource(id = R.drawable.virus_outline),
                            contentDescription = "Virus",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )


                        /*       val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                   val vibratorManager: VibratorManager = LocalContext.current.getSystemService(
                                       Context.
                                       VIBRATOR_MANAGER_SERVICE
                                   ) as VibratorManager
                                   vibratorManager.defaultVibrator

                               } else {
                                   // backward compatibility for Android API < 31,
                                   // VibratorManager was only added on API level 31 release.
                                   @Suppress("DEPRECATION")
                                   LocalContext.current.getSystemService( Context.VIBRATOR_SERVICE) as Vibrator
                               }
                               val DELAY = 0L
                               val VIBRATE = 1000L
                               val SLEEP = 1000L
                               val START = 0
                               val vibratePattern = longArrayOf(DELAY, VIBRATE, SLEEP)

                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                                   vibrator.vibrate(VibrationEffect.createWaveform(vibratePattern, START))



                               } else {
                                   // backward compatibility for Android API < 26
                                   @Suppress("DEPRECATION")
                                   vibrator.vibrate(vibratePattern, START)
                               }

                         */

                    }

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

            MineField(
                tile = Tile.Empty(TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(1, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(2, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(3, TileCoverMode.UNCOVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(2, TileCoverMode.COVERED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(2, TileCoverMode.FLAGGED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })

            MineField(
                tile = Tile.Adjacent(2, TileCoverMode.QUESTIONED, 0, 0),
                onTileSelected = { _, _ -> },
                onTileSelectedSecondary = { _, _ -> })




        }
    }
}







