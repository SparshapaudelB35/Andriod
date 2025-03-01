package com.example.bookstoremanagement.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookstoremanagement.databinding.ActivityNavigationBinding
import com.example.bookstoremanagement.ui.fragment.BookViewFragment
import com.example.bookstoremanagement.ui.fragment.ViewCartFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class NavigationActivity : AppCompatActivity() {

    // Declare the ViewBinding variable
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout
        enableEdgeToEdge()

        // Inflate the ViewBinding
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adjust system bar insets for the root view of the activity
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ViewPager2 and TabLayout
        val viewPager: ViewPager2 = binding.viewPagerMain
        val tabLayout: TabLayout = binding.tabLayoutMain

        // Setup FragmentAdapter
        val adapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = adapter

        // Set up TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "📚 View Books"
                1 -> tab.text = "🛒 View Cart"
            }
        }.attach()
        binding.logoutButton.setOnClickListener {
            logOut()
        }
    }
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

    // Adapter for ViewPager2 to manage fragments
    private inner class ScreenSlidePagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BookViewFragment()
                1 -> ViewCartFragment()
                else -> throw IllegalStateException("Invalid position")
            }
        }
    }
}
