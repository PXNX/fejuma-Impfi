package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.formatTime
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.model.Highscore
import de.fejuma.impfi.ui.MinesweeperTheme


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
private fun ScoreEntryPreview() = MinesweeperTheme {
    ScoreEntry(position = 1, item = Highscore("Username", DifficultyLevel.EASY, 41, 5))
}