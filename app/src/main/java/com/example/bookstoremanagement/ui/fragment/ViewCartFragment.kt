package com.example.bookstoremanagement.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.adapter.CartAdapter
import com.example.bookstoremanagement.utils.CartManager

class ViewCartFragment : Fragment(R.layout.fragment_view_cart) {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_view_cart, container, false)

        // Initialize views
        recyclerView = rootView.findViewById(R.id.recyclerViewCart)
        tvTotalPrice = rootView.findViewById(R.id.tvTotalPrice)
        btnCheckout = rootView.findViewById(R.id.btnCheckout)

        // Setup RecyclerView and Adapter
        cartAdapter = CartAdapter(requireContext(), CartManager.cartItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cartAdapter

        // Update the total price display
        updateTotalPrice()

        // Checkout Button functionality
        btnCheckout.setOnClickListener {
            if (CartManager.cartItems.isNotEmpty()) {
                CartManager.clearCart() // Clear the cart
                Toast.makeText(requireContext(), "Checkout successful!", Toast.LENGTH_SHORT).show()

                // Update UI after clearing the cart
                cartAdapter.updateCartItems(CartManager.cartItems)
                updateTotalPrice()
            } else {
                Toast.makeText(requireContext(), "Cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    // Function to update total price in the UI
    private fun updateTotalPrice() {
        val totalPrice = CartManager.getTotalPrice()
        tvTotalPrice.text = "Total Price: $$totalPrice"
    }

    // Public method to update the cart adapter
    fun updateCartAdapter() {
        cartAdapter.updateCartItems(CartManager.cartItems)
        updateTotalPrice()
    }
}
