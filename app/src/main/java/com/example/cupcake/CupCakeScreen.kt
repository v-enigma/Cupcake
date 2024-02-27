package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

enum class CupCakeScreen(@StringRes val title: Int) {
    Start(title= R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    PickUp(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupCakeAppBar(
    currentScreen: CupCakeScreen,
    canNavigateBack :Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(stringResource(id = currentScreen.title))},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription= stringResource(R.string.back_button)
                    )
                }
            }

        }
    )

}
@Composable
fun CupCakeApp(
    navController: NavHostController = rememberNavController(),
    viewModel: OrderViewModel = viewModel()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupCakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupCakeScreen.Start.name
    )

    Scaffold(
        topBar ={
            CupCakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry!= null,
                navigateUp ={navController.navigateUp()}
            )
        }
    ){ innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupCakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
           composable(route = CupCakeScreen.Start.name){
                StartOrderScreen(
                    modifier = Modifier,
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it);
                        navController.navigate(CupCakeScreen.Flavor.name)
                    }
                )
           }
           composable(route = CupCakeScreen.Flavor.name){
               val context = LocalContext.current
               SelectOptionScreen(
                   total = uiState.price,
                   options = DataSource.flavors,
                   onSelectionChanged = { viewModel.setFlavour(it) },
                   onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                   onNextButtonClicked = { navController.navigate(CupCakeScreen.PickUp.name) }
               )
           }
           composable(route = CupCakeScreen.PickUp.name){
               SelectOptionScreen(
                   total = uiState.price,
                   onNextButtonClicked = { navController.navigate(CupCakeScreen.Summary.name) },
                   options = uiState.pickupOptions,
                   onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                   onSelectionChanged = { viewModel.setDate(it) },
                   modifier = Modifier.fillMaxHeight()
               )
           }
           composable(route = CupCakeScreen.Summary.name){
               val context = LocalContext.current
               OrderSummaryScreen(
                   orderUiState = uiState,
                   modifier = Modifier.fillMaxHeight(),
                   onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                   onSendButtonClicked = { subject: String, summary: String -> shareOrder(context, summary, subject)  }
               )
           }
        }
    }

}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
){
    viewModel.resetOrder()
    navController.popBackStack(CupCakeScreen.Start.name, inclusive = false)

}
private fun shareOrder(context : Context, summary:String, subject:String){
    val intent = Intent(Intent.ACTION_SEND).apply{
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT,subject)
        putExtra(Intent.EXTRA_SUBJECT, summary)

    }
    context.startActivity(
        Intent.createChooser(intent, context.getString(R.string.new_cupcake_order))
    )

}