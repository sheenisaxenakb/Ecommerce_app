package com.ecommerceapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecommerceapp.ui.model.CartItem
import com.ecommerceapp.ui.viewmodel.ECommerceViewModel

@Composable
fun CartScreen(viewModel: ECommerceViewModel, onCheckout: () -> Unit) {
    val cart = viewModel.cart
    val total = cart.sumOf { it.product.price * it.quantity }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("My Basket", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(16.dp))

        LazyColumn {
            itemsIndexed(cart) {index, cartItem ->
                CartItemRow(cartItem, onRemove = { viewModel.removeFromCart(cartItem.product) })
            }
        }

        Text("Total: ₦$total", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Checkout")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("${cartItem.product.name} x ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onRemove) {
            Text("Remove")
        }
    }
}
