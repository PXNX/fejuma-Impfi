package de.fejuma.impfi.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.presentation.game.component.GameEndDialog
import de.fejuma.impfi.presentation.game.component.GameField
import de.fejuma.impfi.presentation.game.component.TopRow
import de.fejuma.impfi.ui.MinesweeperTheme


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val viewModel by viewModels<GameViewModel>()


    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
        ExperimentalFoundationApi::class
    )
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


                        val (openSurrenderDialog, setOpenSurrenderDialog) = remember {
                            mutableStateOf(
                                false
                            )
                        }


                        TopRow(viewModel, setOpenSurrenderDialog)

                        Divider(
                            Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        val (openEndDialog, setOpenEndDialog) = remember { mutableStateOf(false) }


                        GameField(viewModel)






                        if (openEndDialog) {
                            GameEndDialog(false,
                                { findNavController().navigate(R.id.action_game_scoreboard) }, {
                                    setOpenEndDialog(it)
                                }, viewModel
                            )
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