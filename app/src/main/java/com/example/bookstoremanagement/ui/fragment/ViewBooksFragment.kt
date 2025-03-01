package com.example.bookstoremanagement.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.adapter.ProductAdapter
import com.example.bookstoremanagement.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewBooksFragment : Fragment(R.layout.fragment_view_books) {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private val productList: ArrayList<ProductModel> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_view_books, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = productAdapter

        fetchProductsFromRealtimeDatabase()

        return rootView
    }

    private fun fetchProductsFromRealtimeDatabase() {
        val database = FirebaseDatabase.getInstance()
        val productsRef = database.getReference("products")

        // Fetch data from the "products" node
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val products = ArrayList<ProductModel>()
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(ProductModel::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                productAdapter.updateData(products)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ViewBooksFragment", "Error fetching data: ${databaseError.message}")
            }
        })
    }
}
