package de.fejuma.impfi.presentation.scoreboard.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fejuma.impfi.DefaultPreviews
import de.fejuma.impfi.R
import de.fejuma.impfi.difficulties
import de.fejuma.impfi.model.DifficultyLevel
import de.fejuma.impfi.ui.MinesweeperTheme

@Composable
fun ScorePlaceholder(difficultyLevel: DifficultyLevel) =

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


@DefaultPreviews
@Composable
private fun ScoreBoardPreview() = MinesweeperTheme {
    ScorePlaceholder(difficultyLevel = DifficultyLevel.HARD)
}