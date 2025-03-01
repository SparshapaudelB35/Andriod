package com.example.bookstoremanagement.viewmodel

import com.example.bookstoremanagement.model.UserModel
import com.example.bookstoremanagement.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo:UserRepository) {
    fun login(email:String,password:String,
              callback:(Boolean,String)->Unit){
        repo.login(email,password, callback)
    }

    fun signup(email:String,password:String,
               callback: (Boolean, String,String) -> Unit){
        repo.signup(email, password, callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun forgetPassword(email:String,
                       callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun getCurrentUser() : FirebaseUser?{
        return repo.getCurrentUser()
    }
}