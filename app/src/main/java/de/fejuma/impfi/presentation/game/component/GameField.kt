package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import de.fejuma.impfi.presentation.game.GameViewModel
import de.fejuma.impfi.presentation.game.MineFieldState
import kotlin.math.PI
import kotlin.math.abs


@Composable
fun GameField(viewModel: GameViewModel) {
    /*  var isInteractive by remember {
     mutableStateOf(true)
 }

val minScale: Float = 1f
 val maxScale: Float = 3f


 var scale by remember { mutableStateOf(1f) }
 var offset by remember { mutableStateOf(Offset.Zero) }
 var size by remember { mutableStateOf(IntSize.Zero) }
 val state = rememberTransformableState { zoomChange, offsetChange, _ ->
     isInteractive = false
     scale = maxOf(minScale, minOf(scale * zoomChange, maxScale))

     //float.coerceIn() might be nicer
     val maxX = (size.width * (scale - 1)) / 2
     val minX = -maxX
     val offsetX = maxOf(minX, minOf(maxX, offset.x + offsetChange.x * scale))
     val maxY = (size.height * (scale - 1)) / 2
     val minY = -maxY
     val offsetY = maxOf(minY, minOf(maxY, offset.y + offsetChange.y * scale))

     offset = Offset(offsetX, offsetY)

     isInteractive = true
 } */


    var zoom by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }


    Box(
        Modifier
            .fillMaxSize()
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

                        val oldScale = zoom
                        val newScale = (zoom * gestureZoom).coerceIn(0.5f..3f)
                        offset =
                            (offset + gestureCentroid / oldScale) - (gestureCentroid / newScale + gesturePan / oldScale)
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
            }) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(viewModel.board.size),
            modifier = Modifier
                .clipToBounds()
                .fillMaxSize()
                .graphicsLayer {
                    translationX = -offset.x * zoom
                    translationY = -offset.y * zoom
                    scaleX = zoom
                    scaleY = zoom
                }
            //   .background(Color.Cyan)


        ) {


            items(viewModel.board.flatten()) { tile ->


                var fieldState by remember {
                    mutableStateOf(MineFieldState.COVERED)
                }

                MineField(
                    fieldState,
                    tile.nearbyMines,

                    {


//TODO: simplify
                        fieldState = if (tile.isMine) {
                            MineFieldState.VIRUS
                            //set game state lost
                        } else {
                            MineFieldState.NUMBER
                        }
                    },
                    {
                        if (tile.isFlagged) {
                            fieldState = MineFieldState.COVERED
                            tile.isFlagged = false
                        } else {
                            fieldState = MineFieldState.FLAG
                            tile.isFlagged = true
                        }


                    }
                )


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