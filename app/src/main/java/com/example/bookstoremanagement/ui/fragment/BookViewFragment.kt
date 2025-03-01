package com.example.bookstoremanagement.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.adapter.BookAdapter
import com.example.bookstoremanagement.adapter.ProductAdapter
import com.example.bookstoremanagement.model.ProductModel
import com.google.firebase.database.*

class BookViewFragment : Fragment(R.layout.fragment_book_view) {

    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerView: RecyclerView
    private val productList: ArrayList<ProductModel> = ArrayList()

    // Firebase Database reference
    private val database = FirebaseDatabase.getInstance()
    private val productsRef = database.getReference("products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_book_view, container, false)

        // Initialize RecyclerView and Adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewBooks)
        bookAdapter = BookAdapter(requireContext(), productList)
        recyclerView.adapter = bookAdapter

        // Fetch products from Firebase
        fetchProductsFromRealtimeDatabase()

        return rootView
    }

    private fun fetchProductsFromRealtimeDatabase() {
        // Using a ValueEventListener to get real-time data
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val products = ArrayList<ProductModel>()

                // Loop through each child node in the products reference
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(ProductModel::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }

                // Check if products list is not empty
                if (products.isNotEmpty()) {
                    bookAdapter.updateData(products)
                } else {
                    Log.d("BookViewFragment", "No products found in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error while fetching data
                Log.e("BookViewFragment", "Error fetching data: ${databaseError.message}")
                Toast.makeText(requireContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
