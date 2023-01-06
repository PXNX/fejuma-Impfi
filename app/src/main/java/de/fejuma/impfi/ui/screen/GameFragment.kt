package de.fejuma.impfi.ui.screen

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.ui.component.MineField
import de.fejuma.impfi.ui.component.MineFieldState
import de.fejuma.impfi.ui.component.ZoomBox


class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters


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
        binding.composeViewGame.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme() {
                    Column(modifier = Modifier.fillMaxSize()) {


                        Row(
                            Modifier
                                .padding(12.dp)
                                .fillMaxWidth()) {
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

                            Button(onClick = { /*TODO*/ }) {
                                Icon(
                                    painterResource(id = R.drawable.grave_stone),
                                    contentDescription = "",
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Aufgeben")
                            }

                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painterResource(id = R.drawable.cog_outline),
                                    contentDescription = ""
                                )
                            }
                        }



                        Divider(Modifier.fillMaxWidth(), thickness = 2.dp)



                        ZoomBox(
                            Modifier
                                .fillMaxSize()
                                .background(Color.DarkGray)
                        ) {

                            Column(
                                Modifier
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        translationX = offsetX,
                                        translationY = offsetY
                                    ),
                            ) {


                                repeat(14) {

                                    Row {
                                        repeat(8) {
                                            var fieldState by remember {
                                                mutableStateOf(MineFieldState.COVERED)
                                            }

                                            MineField(
                                                fieldState,
                                                {
                                                    fieldState = MineFieldState.VIRUS
                                                },
                                                {
                                                    fieldState = MineFieldState.FLAG
                                                }
                                            )
                                        }
                                    }


                                }


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

