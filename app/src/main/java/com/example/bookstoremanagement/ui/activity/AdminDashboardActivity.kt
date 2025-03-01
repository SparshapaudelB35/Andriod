package com.example.bookstoremanagement.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.databinding.ActivityAdminDashboardBinding
import com.example.bookstoremanagement.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handling window insets (system bars padding)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the ViewPager2 and TabLayout
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        // Set up the ViewPagerAdapter
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        // Set up TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Add Products"
                1 -> tab.text = "Manage Users"
                2 -> tab.text = "Books"
            }
        }.attach()

        // Set up the LogOut button click listener
        binding.logoutButton.setOnClickListener {
            logOut()
        }
    }

    // Function to handle logout logic
    private fun logOut() {
        // Clear session or any saved data
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()  // Clearing session data
        editor.apply()

        // Navigate to the login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Close the current activity
    }
}
