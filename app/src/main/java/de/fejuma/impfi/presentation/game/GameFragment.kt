package de.fejuma.impfi.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.presentation.game.component.MineField
import de.fejuma.impfi.ui.MinesweeperTheme
import de.fejuma.impfi.ui.lightGray


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel by viewModels<GameViewModel>()


    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeViewGame.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MinesweeperTheme {


                    Column(modifier = Modifier.fillMaxSize()) {


                        val openDialog = remember { mutableStateOf(false) }


                        TopRow(viewModel, openDialog)

                        Divider(
                            Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )


                        GameField(viewModel)



                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onCloseRequest.
                                    openDialog.value = false
                                },
                                title = {
                                    Text(text = "Dialog Title")
                                },
                                text = {
                                    Text("Here is a text ")
                                },
                                confirmButton = {
                                    Button(

                                        onClick = {
                                            openDialog.value = false
                                        }) {
                                        Text("This is the Confirm Button")
                                    }
                                },
                                dismissButton = {
                                    Button(

                                        onClick = {
                                            openDialog.value = false
                                        }) {
                                        Text("This is the dismiss Button")
                                    }
                                }
                            )
                        }


                    }

                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRow(
    viewModel: GameViewModel,
    openDialog: MutableState<Boolean>
) {
//todo: using Units (state hoisting) is smoother than passing down state - also when using scope?


    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Row {
                Icon(
                    painterResource(id = R.drawable.alarm),
                    contentDescription = "",
                    tint = lightGray
                )
                Text("4:20", Modifier.padding(start = 10.dp))
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row {
                Icon(
                    painterResource(id = R.drawable.virus_outline),
                    contentDescription = "",

                    )

                Text("10", Modifier.padding(start = 10.dp))
            }
        }

        Button(onClick = {
            openDialog.value = true

        }) {

            Icon(
                painterResource(id = R.drawable.grave_stone),
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Aufgeben")
        }


    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.GameField(viewModel: GameViewModel) {
    var isInteractive by remember {
        mutableStateOf(true)
    }


    val minScale: Float = 1f
    val maxScale: Float = 3f

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
isInteractive=false
        scale = maxOf(minScale, minOf(scale * zoomChange, maxScale))

        val maxX = (size.width * (scale - 1)) / 2
        val minX = -maxX
        val offsetX = maxOf(minX, minOf(maxX, offset.x + offsetChange.x * scale))
        val maxY = (size.height * (scale - 1)) / 2
        val minY = -maxY
        val offsetY = maxOf(minY, minOf(maxY, offset.y + offsetChange.y * scale))

        offset = Offset(offsetX, offsetY)
        isInteractive=true
    }


    /*
        ZoomBox(
            Modifier
                .fillMaxSize(),
            //     .background(Color.DarkGray),
               onInteract =  {
                    isInteractive = it
                }
        ) {

     */
    Box(
        Modifier

            // apply other transformations like rotation and zoom
            // on the pizza slice emoji

            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,

                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .onSizeChanged { size = it }
            .weight(1f, false)

    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            Modifier


                .fillMaxSize(),
        ) {

            items(viewModel.board.value.flatten()) { tile ->

                var fieldState by remember {
                    mutableStateOf(MineFieldState.COVERED)
                }

                MineField(
                    fieldState,
                    isInteractive = isInteractive,
                    {

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

/*    Column(
        Modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX,
                translationY = offsetY
            ),
    ) {

        for (outer in viewModel.board.value){

           Row {
                for (tile in outer){



                }
            }


        }


    }

 */

