package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cupcake.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int,Int>>,
    onNextButtonClicked :(Int) ->Unit,
    modifier: Modifier = Modifier){

        CupCakeScreen(modifier, quantityOptions, onNextButtonClicked)
    }


@Composable
fun CupCakeScreen(modifier: Modifier, quantityOptions: List<Pair<Int, Int>>, onNextButtonClicked: (Int) -> Unit){
    Column(
        Modifier.fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        CupCakeWithText(modifier = modifier)
        Spacer(modifier =Modifier.weight(1f))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)){
            quantityOptions.forEach{
                SelectQuantityButton(labelResourceId = it.first, onClick = { onNextButtonClicked(it.second) })
            }
        }
    }
}

@Composable
fun CupCakeWithText(modifier : Modifier){
    Column( horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier= modifier
        .padding(0.dp)){
        Image(painterResource(id = R.drawable.cupcake), null)
        CupCakeText(modifier = modifier.fillMaxWidth())
    }
}
@Composable
fun CupCakeText(modifier: Modifier){

    Text("Order Cupcakes",modifier = Modifier.padding(10.dp))
}

@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId:Int,
    onClick:() -> Unit,
    modifier: Modifier= Modifier){
    Button( onClick = onClick  , modifier = Modifier.fillMaxWidth()){
        Text(stringResource(labelResourceId))
    }
}