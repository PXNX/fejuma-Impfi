package de.fejuma.impfi.presentation.scoreboard

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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.databinding.FragmentScoreboardBinding
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.ui.component.HighscoreTable

@AndroidEntryPoint
class ScoreboardFragment : Fragment() {

    private val viewModel by viewModels<ScoreboardViewModel>()

    private var _binding: FragmentScoreboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {


                    Column(modifier = Modifier.fillMaxSize()) {

                        CustomTabs(viewModel)

                        Row(
                            Modifier
                                .padding(start = 22.dp, end = 22.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {


                            viewModel.highscores.value?.let {
                                HighscoreTable(scores = it)
                            }
                                ?: Text(text = "Noch keine Highscores in der Schwierigkeit '${difficulties[viewModel.selectedIndex.value]}' verfügbar.")


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

@Composable
fun CustomTabs(viewModel: ScoreboardViewModel) {


    TabRow(
        selectedTabIndex = viewModel.selectedIndex.value,
        /*  backgroundColor = Color(0xff1E76DA),
        modifier = Modifier
             .padding(vertical = 4.dp, horizontal = 8.dp)
             .clip(RoundedCornerShape(50))
             .padding(1.dp),


         indicator = { tabPositions: List<TabPosition> ->
             Box {}
         }  */
    ) {
        difficulties.forEachIndexed { index, difficulty ->
            val selected = viewModel.selectedIndex.value == index
            Tab(
                /*    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.White
                        )
                    else Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color(
                                0xff1E76DA
                            )
                        ),

                 */
                selected = selected,
                onClick = { viewModel.setSelectedIndex(difficulty) },
                text = {
                    Text(
                        text = difficulty.name,
                        //  color = Color(0xff6FAAEE)
                    )
                }
            )
        }
    }

    Text(text = "Selected: ${viewModel.selectedIndex.value}")

    Button(onClick = { viewModel.createEntries() }) {
        Text("Create Entries")
    }
}
