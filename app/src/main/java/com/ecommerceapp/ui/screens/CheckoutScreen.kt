package com.ecommerceapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecommerceapp.ui.viewmodel.ECommerceViewModel

@Composable
fun CheckoutScreen(viewModel: ECommerceViewModel) {
    val total = viewModel.cart.sumOf { it.product.price * it.quantity }
    val discountedTotal = viewModel.applyDiscount()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Checkout", style = MaterialTheme.typography.labelLarge)

        LazyColumn {
            itemsIndexed(viewModel.cart) { index, cartItem ->
                Text("${cartItem.product.name} x ${cartItem.quantity}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewModel.discountCode.value,
            onValueChange = { viewModel.discountCode.value = it },
            label = { Text("Discount Code") }
        )

        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(viewModel.errorMessage.value, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Total: ₦$total", style = MaterialTheme.typography.bodyMedium)
        Text("Discounted Total: ₦$discountedTotal", style = MaterialTheme.typography.bodyMedium)

        Button(
            onClick = { /* Complete order logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Complete Order")
        }
    }
}
