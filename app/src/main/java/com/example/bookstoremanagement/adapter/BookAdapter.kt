package com.example.bookstoremanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.model.ProductModel
import com.example.bookstoremanagement.ui.fragment.ViewCartFragment
import com.example.bookstoremanagement.utils.CartManager
import com.squareup.picasso.Picasso
import com.squareup.picasso.Callback

class BookAdapter(private val context: Context, private var data: ArrayList<ProductModel>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivBookImage)
        val pName: TextView = itemView.findViewById(R.id.tvBookTitleText)
        val pAuthor: TextView = itemView.findViewById(R.id.tvAuthorName)
        val pPrice: TextView = itemView.findViewById(R.id.tvBookPriceValue)
        val pDesc: TextView = itemView.findViewById(R.id.tvBookDescriptionText)
        val addToCartButton: Button = itemView.findViewById(R.id.btnAddToCartAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.book_user, parent, false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val product = data[position]

        // Bind product data
        holder.pName.text = product.productName
        holder.pAuthor.text = product.productAuthor
        holder.pPrice.text = "$${product.price}"
        holder.pDesc.text = product.productDesc

        // Load image using Picasso with error handling
        Picasso.get()
            .load(product.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView, object : Callback {
                override fun onSuccess() {
                    // You can log success here if necessary
                }

                override fun onError(e: Exception?) {
                    // Log error or show a Toast to the user
                    Toast.makeText(context, "Failed to load image.", Toast.LENGTH_SHORT).show()
                }
            })

        // Handle Add to Cart button click
        holder.addToCartButton.setOnClickListener {
            CartManager.addToCart(product)  // Add the product to the cart
            Toast.makeText(context, "${product.productName} added to cart!", Toast.LENGTH_SHORT)
                .show()

            // Update the cart in the fragment
            if (context is AppCompatActivity) {
                val fragment =
                    context.supportFragmentManager.findFragmentByTag(ViewCartFragment::class.java.simpleName) as? ViewCartFragment
                fragment?.updateCartAdapter()  // Call the update method from the fragment
            }
        }

    }

    // Update adapter data
    fun updateData(newData: List<ProductModel>) {
        // Optional: Compare the new data with the current data before updating
        if (newData != data) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }
}
