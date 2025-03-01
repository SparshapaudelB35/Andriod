package com.example.bookstoremanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.model.ProductModel
import com.squareup.picasso.Picasso

class CartAdapter(
    private val context: Context,
    private var data: MutableList<ProductModel>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvCartProductName)
        val productPrice: TextView = view.findViewById(R.id.tvCartProductPrice)
        val productImage: ImageView = view.findViewById(R.id.ivCartProductImage)

        fun bind(product: ProductModel) {
            productName.text = product.productName
            productPrice.text = "$${product.price}"

            Picasso.get()
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = data[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = data.size

    // Update adapter data after a product is added
    fun updateCartItems(newData: List<ProductModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }
}

