package de.fejuma.impfi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fejuma.impfi.R


@Composable
fun DifficultyCard(
    title: String,
    fieldSize: Int,
    minesAmount: Int,
    setActive: () -> Unit,
    isActive: Boolean
){

     var backgroundColor: Color? = null
    var strokeColor: Color? = null

    if (isActive){
        backgroundColor = colorResource(id = R.color.secondary)
        strokeColor = colorResource(id = R.color.primary)
    }
    else{
        backgroundColor = colorResource(id = R.color.lightgray)
        //TODO(MAX): Set Color 4F4F4F; Set FontColor
        strokeColor = colorResource(id = R.color.darkgray)
    }

  Column(modifier = Modifier
      .clip(RoundedCornerShape(4.dp))
      .background(backgroundColor)
      .border(1.dp, strokeColor)
      .clickable { setActive() }
      .padding(2.dp)
      ,
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
      ) {

      Text(text = title,
      fontWeight = FontWeight.Bold,
      fontSize = 20.sp,
      modifier = Modifier.weight(1f, fill = false))
      
      Text(text = "$fieldSize ${stringResource(id = R.string.field_amount)}",
          modifier = Modifier.weight(1f, fill = false))

      Text(text = "$minesAmount ${stringResource(id = R.string.mines_amount)}",
          modifier = Modifier.weight(1f, fill = false))
      

  }

}