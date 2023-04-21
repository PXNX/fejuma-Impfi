package de.fejuma.impfi.presentation.game.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.R
import de.fejuma.impfi.model.Cell
import de.fejuma.impfi.presentation.game.MineFieldState
import de.fejuma.impfi.ui.darkGray


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MineField(
    cell: Cell,


    setState: () -> Unit,
    setFlag: () -> Unit
) {

   // val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(64.dp)
     //       .indication(interactionSource, LocalIndication.current)

            .combinedClickable(

                onClick = {
                    setState()

                },
                onLongClick = {
                  setFlag()
                },
            )
           // .clip(RoundedCornerShape(4.dp))
            .background(if(cell.isShown) Color.Transparent else Color.DarkGray)
           ,

contentAlignment = Alignment.Center

    ) {
        when  {
                cell.isFlagged&&
                        !cell.isShown -> Icon(
                painter = painterResource(id = R.drawable.needle),
                contentDescription = "Flag",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Green
            )

            cell.isShown&&cell.isMine -> Icon(
                painter = painterResource(id = R.drawable.virus_outline),
                contentDescription = "Virus",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Red
            )

            cell.isShown -> Text("${cell.nearbyMines}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            else -> {

            }
        }


    }

}