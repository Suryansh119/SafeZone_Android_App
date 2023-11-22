package com.example.safezoneadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.safezoneadmin.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
   lateinit var binding:ActivityLoginBinding
   lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding=ActivityLoginBinding.inflate(layoutInflater)


    }
}