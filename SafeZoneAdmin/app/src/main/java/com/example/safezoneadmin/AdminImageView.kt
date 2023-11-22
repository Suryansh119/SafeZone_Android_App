package com.example.safezoneadmin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import coil.load

import com.example.safezoneadmin.databinding.ActivityAdminImageViewBinding


class AdminImageView : AppCompatActivity() {
    val binding by lazy {
        ActivityAdminImageViewBinding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.scaleImage.load(intent.getStringExtra("image"))
        binding.textView3.text=intent.getStringExtra("disp")
        binding.imageView2.setOnClickListener {
            finish()
        }
    }
}