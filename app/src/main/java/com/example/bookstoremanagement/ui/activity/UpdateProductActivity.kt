package com.example.bookstoremanagement.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.databinding.ActivityUpdateProductBinding
import com.example.bookstoremanagement.repository.ProductRepositoryImpl
import com.example.bookstoremanagement.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateProductBinding

    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)


        var productId: String? = intent.getStringExtra("productId")


        productViewModel.getProductById(productId.toString())

        productViewModel.products.observe(this) {
            binding.updateProductDesc.setText(it?.productDesc.toString())
            binding.updateProductprice.setText(it?.price.toString())
            binding.updateProductName.setText(it?.productName.toString())
        }

        binding.btnUpdate.setOnClickListener {
            val productName = binding.updateProductName.text.toString()
            val price = binding.updateProductprice.text.toString().toInt()
            val desc = binding.updateProductDesc.text.toString()

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["productName"] = productName
            updatedMap["productDesc"] = desc
            updatedMap["price"] = price

            productViewModel.updateProduct(
                productId.toString(),
                updatedMap
            ) { success, message ->
                if (success) {
                    Toast.makeText(this@UpdateProductActivity,
                        message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateProductActivity,
                        message, Toast.LENGTH_LONG).show()

                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}