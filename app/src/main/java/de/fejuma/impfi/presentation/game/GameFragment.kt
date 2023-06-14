package de.fejuma.impfi.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.formatTime
import de.fejuma.impfi.presentation.game.component.AnimatingCharacter
import de.fejuma.impfi.presentation.game.component.GameEndDialog
import de.fejuma.impfi.presentation.game.component.GameMap
import de.fejuma.impfi.presentation.game.component.TopRow
import de.fejuma.impfi.presentation.game.game.Status
import de.fejuma.impfi.timeLimit
import de.fejuma.impfi.ui.MinesweeperTheme
import kotlin.math.abs


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel by viewModels<GameViewModel>()


    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root


        //fixme: this bleongs to VM, also GAME should maybe be VM?
        //  val game = Game()
        viewModel.configure(
            viewModel.difficulty.width,
            viewModel.difficulty.height,
            viewModel.difficulty.mines
        )



        binding.composeViewGame.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MinesweeperTheme {


                    /*        MainView(
                            time,
                            minesRemaining,
                            map,
                            gameState,
                            { column, row -> game.primaryAction(column, row) },
                            { column, row -> game.secondaryAction(column, row) },
                            { game.configure() },
                        )

                     */





                    Column(modifier = Modifier.fillMaxSize()) {


                        val map by viewModel.gameStateHolder.map.collectAsStateWithLifecycle()
                        val time by viewModel.gameStateHolder.time.collectAsStateWithLifecycle(
                            initialValue = 0
                        )
                        val minesRemaining by viewModel.gameStateHolder.minesRemaining.collectAsStateWithLifecycle()
                        val gameState by viewModel.gameStateHolder.status.collectAsStateWithLifecycle()




                        TopRow(
                            viewModel,
                            {


                                Row {
                                    if (time >= timeLimit) { //end after 1h
                                        //TODO -- maybe end game
                                    } else {
                                        formatTime(time).forEach {
                                            AnimatingCharacter(it)
                                        }
                                    }
                                }



                                viewModel.recordTime?.let { recTime ->

                                    Row {
                                        val timeDifference = time - recTime
                                        val recordTimeFormat =
                                            formatTime(abs(timeDifference))
                                        val color =
                                            if (timeDifference >= 0) Color.Red else Color.Green


                                        AnimatingCharacter(

                                            if (timeDifference >= 0) '+' else '-',
                                            fontSize = 14.sp,
                                            color = color
                                        )

                                        recordTimeFormat.forEach {
                                            AnimatingCharacter(

                                                it,
                                                fontSize = 14.sp,
                                                color = color
                                            )
                                        }


                                    }
                                }


                            }, minesRemaining, viewModel::setOpenSurrenderDialog
                        )

                        Divider(
                            Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        val (openEndDialog, setOpenEndDialog) = remember { mutableStateOf(false) }

                        GameMap(
                            map = map,
                            onTileSelected = viewModel::primaryAction,
                            onTileSelectedSecondary = viewModel::secondaryAction
                        )

                        //     GameField(viewModel) { setOpenEndDialog(true) } //replace with loss diaolog


                        if (gameState == Status.LOST) {
                            GameEndDialog(false,
                                { findNavController().navigate(R.id.startFragment) }, {
                                    setOpenEndDialog(it)
                                }, viewModel, time
                            )
                        }

                        if (gameState == Status.WON) {
                            GameEndDialog(true,
                                { findNavController().navigate(R.id.action_game_scoreboard) }, {
                                    setOpenEndDialog(it)
                                }, viewModel, time
                            )
                        }

                        if (viewModel.isSurrenderDialog) {

                            AlertDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                                    // button. If you want to disable that functionality, simply use an empty
                                    // onCloseRequest.
                                    viewModel.setOpenSurrenderDialog(false)
                                },
                                title = {
                                    Text(stringResource(id = R.string.game_abort_dialog))
                                },
                                /*  text = {
                                      Text("Here is a text ")
                                  }, */
                                confirmButton = {
                                    Button(

                                        onClick = {
                                            viewModel.setOpenSurrenderDialog(false)
                                            findNavController().navigate(R.id.startFragment)
                                        }) {
                                        Text(stringResource(id = R.string.confirm_button))
                                    }
                                },
                                dismissButton = {
                                    Button(

                                        onClick = {

                                            viewModel.setOpenSurrenderDialog(false)

                                        }) {
                                        Text(stringResource(id = R.string.dismiss_button))
                                    }
                                }
                            )
                        }


                    }


                }
            }


        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            viewModel.setOpenSurrenderDialog(true)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        viewModel.isTimerActive = false
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.isSurrenderDialog)
            viewModel.isTimerActive = true
    }


}
