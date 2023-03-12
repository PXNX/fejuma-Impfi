package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import de.fejuma.impfi.R
import de.fejuma.impfi.model.Highscore

@Composable
fun HighscoreTable(
    scores: List<Highscore>
) {

    LazyColumn() {


        item {

            Row() {
                Icon(
                    painterResource(id = R.drawable.account_multiple_outline),
                    contentDescription = stringResource(
                        id = R.string.highscore_user
                    )
                );

                Icon(
                    painterResource(id = R.drawable.alarm), contentDescription = stringResource(
                        id = R.string.highscore_time
                    )
                )
            }
        }

        items(scores) {
            Row() {
                Text(text = it.username)
                Text(text = it.seconds.toString()) //todo: parse to some actual time or pass timestamp
            }
        }

    }


}