package com.example.cupcake.ui


import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {
    private val _uiState = MutableStateFlow( OrderUiState(pickupOptions = pickUpOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setQuantity(numberCupCakes:Int){
        _uiState.update{
            currentState -> currentState.copy(
            quantity = numberCupCakes,
            price = calculatePrice(quantity = numberCupCakes))
        }

    }
    fun setDate(pickUpDate :String){
        _uiState.update{
            currentState  -> currentState.copy(
                date = pickUpDate,
                price = calculatePrice( pickUpDate = pickUpDate)
            )
        }

    }
    fun setFlavour(desiredFlavour:String){
        _uiState.update {  currentState -> currentState.copy(flavor = desiredFlavour) }
    }
    fun resetOrder(){
        _uiState.value = OrderUiState(pickupOptions = pickUpOptions())
    }
    private fun calculatePrice(
        quantity:Int = _uiState.value.quantity,
        pickUpDate: String = _uiState.value.date
    ):String{
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        if( pickUpOptions()[0] == pickUpDate){
            calculatedPrice+= PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    private fun pickUpOptions() : List<String>{
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4){
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}

