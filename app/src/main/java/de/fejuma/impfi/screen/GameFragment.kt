package de.fejuma.impfi.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import de.fejuma.impfi.databinding.FragmentGameBinding
import de.fejuma.impfi.ui.component.DifficultyCard


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
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme() {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            Modifier
                                .padding(start = 22.dp, end = 22.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            var difficultySelection by remember { mutableStateOf(0) }

                            val difficulties = listOf(
                                Difficulty("Leicht", 69, 10),
                                Difficulty("Mittel", 200, 20),
                                Difficulty("Schwer", 400, 30)
                            )

                            repeat(difficulties.size) {
                                // Modifier.weight(1f,false)
                                DifficultyCard(
                                    difficulties[it],
                                    difficultySelection == it
                                ) { difficultySelection = it }
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

data class Difficulty(val name: String, val fieldAmount: Int, val minesAmount: Int)