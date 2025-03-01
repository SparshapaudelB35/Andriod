package com.example.bookstoremanagement.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.databinding.ActivityStartingPageBinding

class StartingPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enabling edge-to-edge
        enableEdgeToEdge()

        // Inflate the layout and bind it to the activity
        binding = ActivityStartingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set OnClickListener for Login button
        binding.loginnvg.setOnClickListener {
            val intent = Intent(this@StartingPageActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener for Register button
        binding.registernvg.setOnClickListener {
            val intent = Intent(this@StartingPageActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }


    }
}
