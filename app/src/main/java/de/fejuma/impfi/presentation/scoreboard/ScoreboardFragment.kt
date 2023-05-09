package de.fejuma.impfi.presentation.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.databinding.FragmentScoreboardBinding
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.presentation.scoreboard.component.HighscoreTable
import de.fejuma.impfi.ui.MinesweeperTheme
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MinesweeperTheme {
                    Column(Modifier.fillMaxSize()) {


                        val pagerState = rememberPagerState()
                        val scope = rememberCoroutineScope()
                        val pages = listOf(
                            DifficultyLevel.EASY, DifficultyLevel.NORMAL, DifficultyLevel.HARD
                        )

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
                                        //   viewModel.getHighscores( pages[index])
                                        //      viewModel.getHighscores(pages[pagerState.currentPage])
                                        scope.launch {
                                            pagerState.scrollToPage(index)
                                        }


                                    },
                                )

                            }/* set current index of tab / difficulties to show entries */

                        }








                        HorizontalPager(
                            pageCount = pages.size,
                            modifier = Modifier.fillMaxSize(),
                            state = pagerState,
                        ) { page ->

                            viewModel.loadHighscores(pages[pagerState.settledPage])

                            HighscoreTable(scores = viewModel.highscores, pages[page])
                            /*    Text("LIST::: ${viewModel.highscores}", color = Color.Magenta)



                                Button(
                     t               onClick = { viewModel.createEntries() },
                                ) {
                                    Text("Create Entries")
                                }

                             */
                        }


                    }
                    // }


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



