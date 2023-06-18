package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.data.repository.RepositoryMock
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.formatTime
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
    difficultyLevel: DifficultyLevel,

    ) {


    scores?.let {

        //Display a scrollable list of scores using LazyColumn
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
            // .background(Color.White)
        ) {

            // Display the header row with icons for "username" and "time"
            //TODO: navigate back

            // Display each score as a row in the table
            itemsIndexed(scores) { key, item ->

                ScoreEntry(key + 1, item)


            }

        }
    } ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painterResource(id = R.drawable.ghost_outline),
                    contentDescription = null,

                    tint = colorResource(id = R.color.darkGreen),
                    modifier = Modifier.size(60.dp)
                )
                Text(

                    text = stringResource(
                        id = R.string.no_score,
                        difficulties[difficultyLevel]!!.name
                    ),
                    color = colorResource(id = R.color.darkGreen),
                    modifier = Modifier.fillMaxSize(.7f),
                    textAlign = TextAlign.Center
                )
            }
        }


    }

}

@Composable
fun ScoreEntry(position: Int, item: Highscore) {
    // Display the score as a card with "username" and "time" columns
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween


        ) {

            val color = when (position) {
                1 -> Color(0xFFC5B358)
                2 -> Color(0xFFC0C0C0)
                3 -> Color(0xffcd7f32)
                else -> Color.Transparent
            }

            // Display the index as the place an the Username
            Text(
                text = "$position", fontSize = 20.sp,
                modifier = Modifier
                    .clip(
                        CardDefaults.shape

                    )
                    .background(color)
                    .padding(16.dp)
                    .fillMaxHeight(),

                )
            Text(
                text = item.username,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(
                        1f
                    )
                    .padding(horizontal = 16.dp)
            )
            //Display the time column
            Divider(
                Modifier
                    .height(32.dp)
                    .width(1.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                // Display the index as the place an the Username


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarm),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = formatTime(item.seconds)) //todo: parse to some actual time or pass timestamp
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.help),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = item.hintsUsed.toString())
                }

            }



        }
    }
}

@DefaultPreviews
@Composable
fun ScoreBoardPreview() {
    MinesweeperTheme {
        val viewModel = ScoreboardViewModel(RepositoryMock)
        HighscoreTable(scores = viewModel.highscores[0], DifficultyLevel.EASY)
    }

}