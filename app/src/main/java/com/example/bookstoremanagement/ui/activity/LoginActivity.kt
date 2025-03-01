package com.example.bookstoremanagement.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookstoremanagement.R
import com.example.bookstoremanagement.databinding.ActivityLoginBinding
import com.example.bookstoremanagement.repository.UserRepositoryImpl
import com.example.bookstoremanagement.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    lateinit var userViewModel: UserViewModel

    private val adminEmail = "admin@bookstore.com"
    private val adminPassword = "admin123"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.btnLogin.setOnClickListener {
            val email: String = binding.editEmail.text.toString().trim()
            val password: String = binding.editPassword.text.toString().trim()

            if (email == adminEmail && password == adminPassword) {
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                userViewModel.login(email, password) { success, message ->
                    if (success) {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        binding.btnSignupnavigate.setOnClickListener {
            val intent = Intent(this@LoginActivity,
                RegistrationActivity::class.java)
            startActivity(intent)

        }

        binding.btnForget.setOnClickListener {
            val intent = Intent(this@LoginActivity,
                ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}