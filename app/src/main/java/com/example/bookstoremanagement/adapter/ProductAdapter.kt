package com.example.bookstoremanagement.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.model.ProductModel
import com.example.bookstoremanagement.ui.activity.UpdateProductActivity
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProductAdapter(private val context: Context, private var data: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productRef = FirebaseDatabase.getInstance().getReference("products")

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivBookCover)
        val pName: TextView = itemView.findViewById(R.id.tvBookTitle)
        val pAuthor: TextView = itemView.findViewById(R.id.tvBookAuthor)
        val pPrice: TextView = itemView.findViewById(R.id.tvBookPrice)
        val pDesc: TextView = itemView.findViewById(R.id.tvBookDescription)
        val editButton: Button = itemView.findViewById(R.id.btnEditBook)
        val deleteButton: Button = itemView.findViewById(R.id.btnDeleteBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data[position]

        // Bind product data
        holder.pName.text = product.productName
        holder.pAuthor.text = product.productAuthor
        holder.pPrice.text = "$${product.price}"
        holder.pDesc.text = product.productDesc

        // Load image with Picasso
        Picasso.get()
            .load(product.imageUrl)
            .placeholder(R.drawable.placeholder) // Placeholder image
            .into(holder.imageView)

        // Edit button click listener
        holder.editButton.setOnClickListener {
            val intent = Intent(context, UpdateProductActivity::class.java)
            intent.putExtra("productId", product.productId)
            context.startActivity(intent)
        }

        // Delete button click listener
        holder.deleteButton.setOnClickListener {
            deleteProduct(position, product.productId)
        }
    }

    // Method to delete a product from Firebase Realtime Database
    private fun deleteProduct(position: Int, productId: String) {
        if (productId.isEmpty()) {
            Toast.makeText(context, "Invalid Product ID", Toast.LENGTH_SHORT).show()
            return
        }

        productRef.child(productId).removeValue()
            .addOnSuccessListener {
                data.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show()
            }
    }

    // Update adapter data
    fun updateData(products: List<ProductModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }
}
