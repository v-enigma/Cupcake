package com.example.cupcake.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.R

@Composable
fun FormattedTotal(total:String, modifier: Modifier){
    Row( modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text( fontSize = 25.sp ,
            text = stringResource(R.string.subtotal_price, total),
            modifier = Modifier.padding(10.dp)
        )
    }

}