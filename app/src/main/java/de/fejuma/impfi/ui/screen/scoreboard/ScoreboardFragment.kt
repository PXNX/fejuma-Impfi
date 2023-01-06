package de.fejuma.impfi.ui.screen.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.fejuma.impfi.databinding.FragmentScoreboardBinding
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.ui.component.HighscoreTable

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

                        CustomTabs()

                        Row(
                            Modifier
                                .padding(start = 22.dp, end = 22.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            val scores = listOf(
                                Highscore(1, "Rainer Zufall",DifficultyLevel.EASY, 69),
                                Highscore(2, "Do Ping", DifficultyLevel.NORMAL, 420)
                            )

                            HighscoreTable(difficultyName = "Einfach", scores = scores)

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
fun CustomTabs() {
    var selectedIndex by remember { mutableStateOf(0) }

    val list = listOf("Active", "Completed")

    TabRow(selectedTabIndex = selectedIndex,
        backgroundColor = Color(0xff1E76DA),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50))
            .padding(1.dp),
        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) Modifier
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
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }
    }
    
    Text(text = "Selected: ${selectedIndex}")
}
