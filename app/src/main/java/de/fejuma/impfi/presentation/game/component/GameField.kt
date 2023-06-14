package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.presentation.game.GameViewModel
import de.fejuma.impfi.presentation.game.game.Game
import de.fejuma.impfi.presentation.game.game.Tile
import kotlin.math.PI
import kotlin.math.abs


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun GameMap(
    map: List<List<Tile>>,
    onTileSelected: (column: Int, row: Int) -> Unit,
    onTileSelectedSecondary: (column: Int, row: Int) -> Unit,
) {

    var zoom by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }


    Box(
        Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {

                //zoom in/out and move around
                detectTransformGestures(
                    pass = PointerEventPass.Initial,
                    onGesture = { gestureCentroid: Offset,
                                  gesturePan: Offset,
                                  gestureZoom: Float,
                                  _,
                                  _,
                                  changes: List<PointerInputChange> ->


                        // Limit the amount of zoom
                        val newScale = (zoom * gestureZoom).coerceIn(0.5f, 3f)

                        // Limit how much the child can be dragged within its parent
                        val maxX = abs((size.width - parentSize.width * newScale) / 2)
                        val maxY = abs((size.height - parentSize.height * newScale) / 2)

                        /*  val newOffset =
                              (offset + gestureCentroid / zoom) - (gestureCentroid / newScale + gesturePan / zoom) */

                        val newOffset = offset + gesturePan

                        offset = Offset(
                            newOffset.x.coerceIn(-maxX, maxX),
                            newOffset.y.coerceIn(-maxY, maxY)
                        )

                        zoom = newScale


// 🔥Consume touch when multiple fingers down
// This prevents click and long click if your finger touches a
// button while pinch gesture is being invoked
                        val size = changes.size
                        if (size > 1) {
                            changes.forEach { it.consume() }
                        }
                    }
                )
            }, contentAlignment = Alignment.Center
    ) {


        /*     Column(
                 Modifier     .onSizeChanged {
                 parentSize = it
             }

                 .graphicsLayer {
                     translationX = offset.x
                     translationY = offset.y
                     scaleX = zoom
                     scaleY = zoom
                 }
             ){
             (1..60).chunked(6).forEach{
                 x->


                     Row{
                         x.forEach{ y->

                             val col =   when(y%5) {
                                 0 -> Color.Green
                                 1 -> Color.Red

                                 2->Color.Yellow

                                3-> Color.LightGray
                                 else -> Color.Transparent
                             }

                             var mod = Modifier.padding(4.dp).clip(
                                 CircleShape)

                             if (y%5==4 || y%5==4){
                                 mod = mod.border(2.dp,Color.LightGray, shape = CircleShape)
                             }



                             Box(mod.background(col).size(42.dp).padding(8.dp), contentAlignment = Alignment.Center){



                         when(y%5){
                             0-> Icon(
                                 painterResource(id = R.drawable.needle),
                                 modifier = Modifier.fillMaxSize(),
                                 tint=Color.Black,
                                 contentDescription = null,

                             )
                             1-> Icon(
                                 painterResource(id = R.drawable.virus_outline),
                                 modifier = Modifier.fillMaxSize(),
                                 contentDescription = null,
                                 tint=Color.White,
                             )
                             2-> {
                                 Icon(
                                     painterResource(id = R.drawable.help),
                                     modifier = Modifier.fillMaxSize(),
                                     contentDescription = null,
                                     tint=Color.Black,
                                 )
                             }
                             else-> {

                             }
                         }      }


                     }
                 }}
             }

         */


        /*    Surface(onClick = { /*TODO*/ }, modifier = Modifier     .onSizeChanged {
                sizee = it
            }


                .graphicsLayer {
                    translationX = -offset.x
                    translationY = -offset.y
                    scaleX = zoom
                    scaleY = zoom
                }) {
                Text("YEEE",Modifier.background(Color.Cyan).padding(10.dp))
            }

         */


        Column(

            modifier = Modifier
                //   .clipToBounds()
                //      .wrapContentSize(unbounded = true)
                //       .fillMaxSize()
                //       .clipToBounds()


                .onSizeChanged {
                    parentSize = it
                }

                .graphicsLayer {
                    translationX = offset.x
                    translationY = offset.y
                    scaleX = zoom
                    scaleY = zoom
                }


            //   .background(Color.Cyan)


        ) {

            map.forEach { row ->
                LazyRow {
                    items(row) { cell ->


                        MineField(
                            cell,

                            onTileSelected,
                            onTileSelectedSecondary
                        )

                    }
                }
            }
        }


    }
}


internal suspend fun PointerInputScope.detectTransformGestures(
    panZoomLock: Boolean = false,
    consume: Boolean = true,
    pass: PointerEventPass = PointerEventPass.Main,
    onGestureStart: (PointerInputChange) -> Unit = {},
    onGesture: (
        centroid: Offset,
        pan: Offset,
        zoom: Float,
        rotation: Float,
        mainPointer: PointerInputChange,
        changes: List<PointerInputChange>
    ) -> Unit,
    onGestureEnd: (PointerInputChange) -> Unit = {}
) {
    awaitEachGesture {
        var rotation = 0f
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        var lockedToPanZoom = false

        // Wait for at least one pointer to press down, and set first contact position
        val down: PointerInputChange = awaitFirstDown(
            requireUnconsumed = false,
            pass = pass
        )
        onGestureStart(down)

        var pointer = down
        // Main pointer is the one that is down initially
        var pointerId = down.id

        do {
            val event = awaitPointerEvent(pass = pass)

            // If any position change is consumed from another PointerInputChange
            // or pointer count requirement is not fulfilled
            val canceled =
                event.changes.any { it.isConsumed }

            if (!canceled) {

                // Get pointer that is down, if first pointer is up
                // get another and use it if other pointers are also down
                // event.changes.first() doesn't return same order
                val pointerInputChange =
                    event.changes.firstOrNull { it.id == pointerId }
                        ?: event.changes.first()

                // Next time will check same pointer with this id
                pointerId = pointerInputChange.id
                pointer = pointerInputChange

                val zoomChange = event.calculateZoom()
                val rotationChange = event.calculateRotation()
                val panChange = event.calculatePan()

                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    rotation += rotationChange
                    pan += panChange

                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = abs(1 - zoom) * centroidSize
                    val rotationMotion =
                        abs(rotation * PI.toFloat() * centroidSize / 180f)
                    val panMotion = pan.getDistance()

                    if (zoomMotion > touchSlop ||
                        rotationMotion > touchSlop ||
                        panMotion > touchSlop
                    ) {
                        pastTouchSlop = true
                        lockedToPanZoom = panZoomLock && rotationMotion < touchSlop
                    }
                }

                if (pastTouchSlop) {
                    val centroid = event.calculateCentroid(useCurrent = false)
                    val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                    if (effectiveRotation != 0f ||
                        zoomChange != 1f ||
                        panChange != Offset.Zero
                    ) {
                        onGesture(
                            centroid,
                            panChange,
                            zoomChange,
                            effectiveRotation,
                            pointer,
                            event.changes
                        )
                    }

                    if (consume) {
                        event.changes.forEach {
                            if (it.positionChanged()) {
                                it.consume()
                            }
                        }
                    }
                }
            }
        } while (!canceled && event.changes.any { it.pressed })
        onGestureEnd(pointer)
    }
}

@Preview
@Composable
fun GameFieldPreview() {
    val viewModel = GameViewModel(RepositoryMock)

    val game = Game()
    game.configure(20, 10, 20)


    val map by game.gameStateHolder.map.collectAsState()

    //   viewModel.startGame(10,20,10)
    GameMap(map,
        { column, row -> game.primaryAction(column, row) },
        { column, row -> game.secondaryAction(column, row) }
    )
}

