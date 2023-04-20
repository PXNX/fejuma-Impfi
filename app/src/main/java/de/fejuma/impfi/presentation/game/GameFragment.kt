package de.fejuma.impfi.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.presentation.game.component.GameEndDialog
import de.fejuma.impfi.presentation.game.component.MineField
import de.fejuma.impfi.presentation.game.component.TopRow
import de.fejuma.impfi.ui.MinesweeperTheme


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


                        val (openSurrenderDialog, setOpenSurrenderDialog) = remember { mutableStateOf(false) }


                        TopRow(viewModel, setOpenSurrenderDialog)

                        Divider(
                            Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        val (openEndDialog, setOpenEndDialog) = remember { mutableStateOf(false) }

                        GameField(viewModel) {
                            setOpenEndDialog
                        }



                        if (openEndDialog) {
                            GameEndDialog(false,
                                {findNavController().navigate(R.id.action_game_scoreboard)}, {
                                setOpenEndDialog(it)
                            }, viewModel)
                        }

                        if (openSurrenderDialog) {
                            AlertDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onCloseRequest.
                                    setOpenSurrenderDialog(false)
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
                                            setOpenSurrenderDialog(false)
                                        }) {
                                        Text("This is the Confirm Button")
                                    }
                                },
                                dismissButton = {
                                    Button(

                                        onClick = {
                                            setOpenSurrenderDialog(false)
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




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColumnScope.GameField(viewModel: GameViewModel, setActive: () -> (Boolean) -> Unit) {
    var isInteractive by remember {
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

        val maxX = (size.width * (scale - 1)) / 2
        val minX = -maxX
        val offsetX = maxOf(minX, minOf(maxX, offset.x + offsetChange.x * scale))
        val maxY = (size.height * (scale - 1)) / 2
        val minY = -maxY
        val offsetY = maxOf(minY, minOf(maxY, offset.y + offsetChange.y * scale))

        offset = Offset(offsetX, offsetY)
        isInteractive = true
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

