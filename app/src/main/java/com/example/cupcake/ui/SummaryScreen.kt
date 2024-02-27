package com.example.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.Components.FormattedTotal

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked :() -> Unit,
    onSendButtonClicked :(String, String) -> Unit,
    modifier: Modifier = Modifier
){

  val resources = LocalContext.current.resources

  val  numberOfCupcakes = resources.getQuantityString(
      R.plurals.cupcakes, orderUiState.quantity,orderUiState.quantity )

  val items = listOf(
      Pair(stringResource(R.string.quantity), numberOfCupcakes),
      Pair(stringResource(R.string.flavor), orderUiState.flavor),
      Pair(stringResource(R.string.pickup_date),orderUiState.date)
  )
Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            items.forEach { item ->
                Text(item.first.uppercase(), modifier = Modifier.padding(top = 10.dp, bottom =5.dp))
                Text(text = item.second, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top =5.dp, bottom= 10.dp))
                Divider(thickness = 2.dp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            FormattedTotal(total = "45", modifier = Modifier.align(Alignment.End))

        }
        Row(modifier = Modifier.padding(15.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked("${orderUiState.flavor} ${orderUiState.quantity}  ${orderUiState.price}"," Summary") }
                ) {
                    Text(stringResource(id = R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {onCancelButtonClicked()}
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }

}

