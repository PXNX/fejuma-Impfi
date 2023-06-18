package de.fejuma.impfi.presentation.scoreboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import de.fejuma.impfi.R
import de.fejuma.impfi.databinding.FragmentScoreboardBinding
import de.fejuma.impfi.difficulties
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
        binding.composeViewScoreboard.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MinesweeperTheme {


                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(Modifier.fillMaxSize()) {


                            val pages = difficulties.keys


                            val pagerState = rememberPagerState(
                                initialPage = 0,
                                initialPageOffsetFraction = 0f
                            ) {
                                pages.size
                            }

                            val scope = rememberCoroutineScope()


                            TabRow(
                                // Our selected tab is our current page
                                selectedTabIndex = pagerState.currentPage,
                                // Override the indicator, using the provided pagerTabIndicatorOffset modifier

                            ) {
                                // Add tabs for all of our pages
                                pages.forEachIndexed { index, title ->
                                    Tab(
                                        text = { Text(difficulties[title]!!.name) },
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
                                modifier = Modifier.fillMaxSize(),
                                state = pagerState,
                                pageSpacing = 0.dp,

                                userScrollEnabled = true,
                                reverseLayout = false,
                                contentPadding = PaddingValues(0.dp),
                                beyondBoundsPageCount = 0,
                                pageSize = PageSize.Fill,
                                //   flingBehavior = PagerDefaults.flingBehavior(state = state),
                                //     key = pagerState.currentPage,
                                pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                                    Orientation.Horizontal
                                ),
                            ) { page ->
                                Log.e("SCORE", "page: $page -- size: ${viewModel.highscores.size}")



                                HighscoreTable(
                                    scores = viewModel.highscores[page],
                                    pages.elementAt(page)
                                )

                            }


                        }

                        ExtendedFloatingActionButton(
                            onClick = { findNavController().navigateUp() },

                            ) {
                            Icon(
                                painterResource(id = R.drawable.arrow_left_thin_circle_outline),
                                contentDescription = "",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Zur Startseite")
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



