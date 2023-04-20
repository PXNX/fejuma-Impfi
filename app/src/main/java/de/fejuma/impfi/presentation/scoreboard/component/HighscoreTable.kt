package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.R
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.model.difficulties
import de.fejuma.impfi.presentation.scoreboard.ScoreboardViewModel

@Composable
fun HighscoreTable(
    scores: List<Highscore>, viewModel: ScoreboardViewModel
) {

    //Check if there are any scores to display
    if (scores.isNotEmpty()) {
        //Display a scrollable list of scores using LazyColumn
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            // .background(Color.White)
        ) {

            // Display the header row with icons for "username" and "time"
            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp, 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    // Icon for "username" column
                    Icon(
                        painterResource(id = R.drawable.account_multiple_outline),
                        contentDescription = stringResource(
                            id = R.string.highscore_user
                        ),
                        modifier = Modifier.size(40.dp),
                        tint = colorResource(id = R.color.darkGreen)

                    )


                    // Icon for "time" column
                    Icon(
                        painterResource(id = R.drawable.alarm),
                        contentDescription = stringResource(
                            id = R.string.highscore_time
                        ),
                        modifier = Modifier.size(40.dp),
                        tint = colorResource(id = R.color.darkGreen)
                    )


                }

            }
            // Display each score as a row in the table
            items(scores) {

                // Get the index of the current score in the list and increment +1
                var index = scores.indexOf(it)
                index++

                // Display the score as a card with "username" and "time" columns
                Card() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp, 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Display the index as the place an the Username
                        Text(text = index.toString() + ". " + it.username)
                        //Display the time column
                        Text(text = it.seconds.toString()) //todo: parse to some actual time or pass timestamp


                    }
                }

            }

        }


    } else {
        // If there are no scores to display, show a message with the selected difficulty level
        Text(
            text = stringResource(
                id = R.string.no_score, difficulties[viewModel.selectedIndex.value].name
            ), color = colorResource(id = R.color.darkGreen)

        )
    }
}