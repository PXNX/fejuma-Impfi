package de.fejuma.impfi.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.model.getFieldState
import de.fejuma.impfi.presentation.game.component.MineField
import de.fejuma.impfi.presentation.game.component.ZoomBox
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel by viewModels<GameViewModel>()


    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalMaterialApi::class)
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
                MaterialTheme() {

                    val sheetState = rememberModalBottomSheetState(
                        ModalBottomSheetValue.Hidden
                    )


                    ModalBottomSheetLayout(sheetContent = {

                        Text("ingame settings")

                    }, sheetState = sheetState) {


                        Column(modifier = Modifier.fillMaxSize()) {


                            val openDialog = remember { mutableStateOf(false) }


                            TopRow(viewModel, sheetState, openDialog)

                            Divider(Modifier.fillMaxWidth(), thickness = 2.dp)


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
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopRow(
    viewModel: GameViewModel,
    sheetState: ModalBottomSheetState,
    openDialog: MutableState<Boolean>
) {
//todo: using Units (state hoisting) is smoother than passing down state - also when using scope?


    val scope = rememberCoroutineScope()

    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.weight(1f)) {
            Row {
                Icon(
                    painterResource(id = R.drawable.alarm),
                    contentDescription = ""
                )
                Text("4:20", Modifier.padding(start = 10.dp))
            }

            Row(Modifier.padding(top = 6.dp)) {
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

        IconButton(onClick = {

            scope.launch {
                sheetState.show()
            }

        }) {
            Icon(
                painterResource(id = R.drawable.cog_outline),
                contentDescription = ""
            )
        }
    }

}

@Composable
fun GameField(viewModel: GameViewModel) {


    ZoomBox(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        LazyColumn(
            Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
        ) {

            items( viewModel.board.value) {outer->

                LazyRow {
                    items(outer) { tile->



                        var fieldState by remember {
                            mutableStateOf(MineFieldState.COVERED)
                        }

                        MineField(
                            fieldState,
                            {

                                if(tile.isMine){
                                    fieldState = MineFieldState.VIRUS
                                    //set game state lost
                                }

                                else{
                                    fieldState=MineFieldState.NUMBER
                                }
                            },
                            {
                                 if (tile.isFlagged){
                                     fieldState =  MineFieldState.COVERED
                                     tile.isFlagged=false
                                 }

                                else{
                                     fieldState =  MineFieldState.FLAG
                                     tile.isFlagged=true
                                 }


                            }
                        )
                    }
                }


            }


        }

    }

}
