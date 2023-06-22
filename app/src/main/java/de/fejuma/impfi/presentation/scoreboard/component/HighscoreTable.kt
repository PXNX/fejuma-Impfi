package de.fejuma.impfi.presentation.scoreboard.component

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

@Composable
fun HighscoreTable(
    scores: List<Highscore>?,
    difficultyLevel: DifficultyLevel
) {
    // Let captures a non-null value
    scores?.let {
        if (scores.isNotEmpty()) {
            //Display a scrollable list of scores using LazyColumn
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                // We don't want element of the list to be too close to each other, so we space them
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
    }
    // The elvis operator (turn your head to the left, you will see a little face) allows us
    // to respond to when a given value is null, here we just show that no Highscores for a
    // given difficulty are present yet.
        ?: ScorePlaceholder(difficultyLevel)
}


@DefaultPreviews
@Composable
private fun ScoreBoardPreview() = MinesweeperTheme {
    val viewModel = ScoreboardViewModel(RepositoryMock)
    HighscoreTable(scores = viewModel.highscores[0], DifficultyLevel.EASY)
}