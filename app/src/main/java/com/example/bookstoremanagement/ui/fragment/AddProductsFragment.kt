package com.example.bookstoremanagement.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.databinding.FragmentAddProductsBinding
import com.example.bookstoremanagement.model.ProductModel
import com.example.bookstoremanagement.repository.ProductRepositoryImpl

class AddProductsFragment : Fragment(R.layout.fragment_add_products) {

    private var _binding: FragmentAddProductsBinding? = null
    private val binding get() = _binding!!
    private val productRepository = ProductRepositoryImpl()

    private var imageUri: Uri? = null  // To store the selected image URI

    private val PICK_IMAGE_REQUEST = 71  // Code for image picker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductsBinding.inflate(inflater, container, false)

        // Handle Image Upload Button Click
        binding.uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        // Handle Add Product Button Click
        binding.addProductButton.setOnClickListener {
            val productName = binding.productName.text.toString()
            val productAuthor = binding.productAuthor.text.toString()
            val productDesc = binding.productDescription.text.toString()
            val productPrice = binding.productPrice.text.toString().toIntOrNull() ?: 0

            if (productName.isNotEmpty() && productAuthor.isNotEmpty() && productDesc.isNotEmpty()) {
                if (imageUri != null) {
                    uploadImageAndSaveProduct(productName, productAuthor, productDesc, productPrice)
                } else {
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            binding.previewImageView.setImageURI(imageUri) // Preview the selected image
        }
    }

    // Upload the image and then save the product to Firebase
    private fun uploadImageAndSaveProduct(
        productName: String,
        productAuthor: String,
        productDesc: String,
        productPrice: Int
    ) {
        imageUri?.let {
            productRepository.uploadImage(requireContext(), it) { imageUrl ->
                if (imageUrl != null) {
                    val product = ProductModel(
                        productId = "",  // Firebase will generate a unique ID
                        productName = productName,
                        productAuthor = productAuthor,
                        productDesc = productDesc,
                        price = productPrice,
                        imageUrl = imageUrl  // Use the uploaded image URL
                    )

                    // Add product to Firebase
                    productRepository.addProduct(product) { success, message ->
                        if (success) {
                            Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()
                            clearFields()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun clearFields() {
        binding.productName.text?.clear()
        binding.productAuthor.text?.clear()
        binding.productDescription.text?.clear()
        binding.productPrice.text?.clear()
        binding.previewImageView.setImageURI(null) // Clear the image preview
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding reference to avoid memory leaks
    }
}
