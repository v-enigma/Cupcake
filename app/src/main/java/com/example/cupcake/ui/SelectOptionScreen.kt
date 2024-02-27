package com.example.cupcake.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cupcake.ui.Components.FormattedTotal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectOptionScreen(
    total:String,
    options : List<String>,
    onSelectionChanged :(String) -> Unit ={},
    onNextButtonClicked :()-> Unit ={},
    onCancelButtonClicked :() -> Unit ={},
    modifier: Modifier = Modifier
){
    var selectedValue by rememberSaveable { mutableStateOf("") }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Choose Flavour", modifier = Modifier.padding(10.dp)) },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
//                navigationIcon = {Image( imageVector = Icons.Filled.ArrowBack,"", modifier = Modifier.padding(10.dp)) }
//            )
//        }
//    ){

          Column(modifier){
                options.forEach{
                    item ->
                    Row(verticalAlignment = Alignment.CenterVertically){
                        RadioButton(selected = selectedValue == item, onClick = { selectedValue = item ;onSelectionChanged(selectedValue)})
                        Text(text =item)
                    }
                }
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(10.dp)
                )
                FormattedTotal(
                    total,
                    Modifier
                        .align(alignment = Alignment.End)
                        .fillMaxWidth()
                )
                Spacer( modifier = Modifier.weight(1f))
                Controls(selectedValue,onCancelButtonClicked,onNextButtonClicked)

          }
    //}

}
@Composable
fun Controls(selectedValue :String, onCancelButtonClicked: () -> Unit, onNextButtonClicked: () -> Unit){
    //var background = rememberSaveable{ Color.Transparent}
    Row(modifier = Modifier.padding(10.dp)){
            OutlinedButton(modifier = Modifier.weight(1f),onClick = {onCancelButtonClicked() }){
                Text("Cancel")
            }
           Spacer(modifier = Modifier.padding(5.dp))
            Button(modifier = Modifier.weight(1f),onClick ={ onNextButtonClicked()},enabled = selectedValue.isNotEmpty(),  ){
                Text( "Next")
            }
    }

}
