package com.example.bookstoremanagement.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bookstoremanagement.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookstoremanagement.databinding.FragmentManageUsersBinding
import com.example.bookstoremanagement.model.UserModel
import com.example.bookstoremanagement.adapter.UserAdapter
import com.google.firebase.database.*

class ManageUsersFragment : Fragment(R.layout.fragment_manage_users) {

    private var _binding: FragmentManageUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private val usersList = mutableListOf<UserModel>()
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageUsersBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewUsers.layoutManager = layoutManager

        // Initialize adapter and set it to RecyclerView
        userAdapter = UserAdapter(usersList) { user ->
            deleteUser(user)
        }
        binding.recyclerViewUsers.adapter = userAdapter

        fetchUsersFromFirebase()

        return binding.root
    }

    private fun fetchUsersFromFirebase() {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    user?.let { usersList.add(it) }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(user: UserModel) {
        val userRef = usersRef.child(user.userId)
        userRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
