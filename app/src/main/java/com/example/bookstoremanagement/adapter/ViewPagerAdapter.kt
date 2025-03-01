package com.example.bookstoremanagement.ui.adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookstoremanagement.ui.activity.AdminDashboardActivity
import com.example.bookstoremanagement.ui.fragment.AddProductsFragment
import com.example.bookstoremanagement.ui.fragment.ManageUsersFragment
import com.example.bookstoremanagement.ui.fragment.ViewBooksFragment

class ViewPagerAdapter(activity: AdminDashboardActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3 // We have 2 tabs: Add Products and Manage Users

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddProductsFragment()
            1 -> ManageUsersFragment()
            2 -> ViewBooksFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}
