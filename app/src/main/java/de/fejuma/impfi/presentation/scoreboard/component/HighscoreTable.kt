package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.formatTime
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.presentation.scoreboard.ScoreboardViewModel

//TODO: What about having statistics and e.g. top 3 rounds here instead?
// or what about also having the Date/Time at which a core was achieved saved here?
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighscoreTable(
    scores: List<Highscore>,
    difficultyLevel: DifficultyLevel
) {

    if (scores.isNotEmpty()) {

        //Display a scrollable list of scores using LazyColumn
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
            // .background(Color.White)
        ) {

            // Display the header row with icons for "username" and "time"
            stickyHeader {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(Color.DarkGray)
                        .padding(4.dp, 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    // Icon for "username" column
                    Icon(
                        painterResource(id = R.drawable.account_multiple_outline),
                        contentDescription = stringResource(
                            id = R.string.highscore_user
                        ),

                        tint = colorResource(id = R.color.darkGreen)

                    )


                    // Icon for "time" column
                    Icon(
                        painterResource(id = R.drawable.alarm),
                        contentDescription = stringResource(
                            id = R.string.highscore_time
                        ),

                        tint = colorResource(id = R.color.darkGreen)
                    )


                }

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp), color = Color.White
                )
            }
            // Display each score as a row in the table
            itemsIndexed(scores) { key, item ->


                // Display the score as a card with "username" and "time" columns
                Card() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp, 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Display the index as the place an the Username
                        Text(text = "${key + 1}. ${item.username}")
                        //Display the time column
                        Text(text = formatTime(item.seconds.toLong())) //todo: parse to some actual time or pass timestamp


                    }
                }

            }

        }
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(id = R.drawable.ghost_outline),
                    contentDescription = null,

                    tint = colorResource(id = R.color.darkGreen),
                    modifier = Modifier.size(60.dp)
                )
                Text(

                    text = stringResource(id = R.string.no_score, difficultyLevel),
                    color = colorResource(id = R.color.darkGreen),
                    modifier = Modifier.fillMaxSize(.7f),
                    textAlign = TextAlign.Center
                )
            }
        }

    }


}

@DefaultPreviews
@Composable
fun ScoreBoardPreview() {
    val viewModel = ScoreboardViewModel(RepositoryMock)
  //  viewModel.loadHighscores(difficulties[0].level)
    HighscoreTable(scores = viewModel.highscores, difficulties[0].level)
}