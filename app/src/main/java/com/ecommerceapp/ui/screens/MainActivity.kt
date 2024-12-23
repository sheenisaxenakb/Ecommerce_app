package com.ecommerceapp.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecommerceapp.network.ProductRepository
import com.ecommerceapp.network.RetrofitClient
import com.ecommerceapp.ui.theme.EcommerceAppTheme
import com.ecommerceapp.ui.viewmodel.ECommerceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)){
                        MainApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val repository = ProductRepository(RetrofitClient.api)
    val viewModel = ECommerceViewModel(repository)


    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            ProductListScreen(viewModel, onCartClick = { navController.navigate("cart") })
        }
        composable("cart") {
            CartBasketScreen(viewModel = viewModel, onCheckoutClick = { navController.navigate("checkout")}, onBackClick = { navController.popBackStack() })
        }
        composable("checkout") {
            CheckoutScreen(viewModel = viewModel)
        }
    }
   /* var currentScreen by remember { mutableStateOf("ProductList") }

    when (currentScreen) {
        "ProductList" -> ProductListScreen(viewModel = viewModel, onCartClick = { currentScreen = "Cart" })
        "Cart" -> CartScreen(viewModel = viewModel, onCheckout = { currentScreen = "Checkout" })
        "Checkout" -> CheckoutScreen(viewModel = viewModel)
    }*/
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    EcommerceAppTheme {
        MainApp()
    }
}