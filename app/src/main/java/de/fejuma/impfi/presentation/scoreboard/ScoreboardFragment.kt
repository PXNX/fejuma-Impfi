package de.fejuma.impfi.presentation.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.databinding.FragmentScoreboardBinding
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.ui.component.HighscoreTable
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScoreboardFragment : Fragment() {

    private val viewModel by viewModels<ScoreboardViewModel>()

    private var _binding: FragmentScoreboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalFoundationApi::class)
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
Column(Modifier.fillMaxSize()) {





                    val pagerState = rememberPagerState()
                    val scope = rememberCoroutineScope()
                    val pages = listOf(DifficultyLevel.EASY,DifficultyLevel.NORMAL,DifficultyLevel.HARD)

                    TabRow(
                        // Our selected tab is our current page
                        selectedTabIndex = pagerState.currentPage,
                        // Override the indicator, using the provided pagerTabIndicatorOffset modifier

                    ) {
                        // Add tabs for all of our pages
                        pages.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title.toString()) },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    scope.launch {
                                        pagerState.scrollToPage(index)
                                    }


                                },
                            )
                        }
                    }

                    HorizontalPager(
                        pageCount = pages.size,
                        modifier = Modifier.fillMaxSize(),
                        state = pagerState,
                    ) { page -> Column(Modifier.fillMaxSize()) {
                        

                        Text(text = "Selected: ${viewModel.selectedIndex.value}")

                        Button(onClick = { viewModel.createEntries() }) {
                            Text("Create Entries")
                        }

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




                }}
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
