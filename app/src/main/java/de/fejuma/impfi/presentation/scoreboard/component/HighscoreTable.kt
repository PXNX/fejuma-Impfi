package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.presentation.scoreboard.ScoreboardViewModel
import de.fejuma.impfi.ui.MinesweeperTheme

//TODO: What about having statistics and e.g. top 3 rounds here instead?
// or what about also having the Date/Time at which a core was achieved saved here?
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighscoreTable(
    scores: List<Highscore>?,
    difficultyLevel: DifficultyLevel
) {
    scores?.let {
        if (scores.isNotEmpty()) {
            //Display a scrollable list of scores using LazyColumn
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // Display each score as a row in the table
                itemsIndexed(it) { key, item ->
                    ScoreEntry(key + 1, item)
                }
            }
        } else {
            ScorePlaceholder(difficultyLevel)
        }


    } ?: ScorePlaceholder(difficultyLevel)
}


@DefaultPreviews
@Composable
private fun ScoreBoardPreview() = MinesweeperTheme {
    val viewModel = ScoreboardViewModel(RepositoryMock)
    HighscoreTable(scores = viewModel.highscores[0], DifficultyLevel.EASY)
}