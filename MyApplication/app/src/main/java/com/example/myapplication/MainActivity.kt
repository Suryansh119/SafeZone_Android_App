package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

import java.util.UUID


class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var person=PersonModel()
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                binding.spinKit.visibility= View.VISIBLE
                val fileUri = data?.data!!
                Firebase.storage.reference.child("Reporting/${UUID.randomUUID()}").putFile(fileUri).addOnCompleteListener {
                    if (it.isSuccessful){
                        it.result.storage.downloadUrl.addOnSuccessListener {
                            person.ImageUrl=it.toString()
                            binding.uploadImage.setImageURI(fileUri)
                            binding.spinKit.visibility= View.INVISIBLE
                        }
                    }
                }


            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.uploadImage.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)

                }
         binding.submit.setOnClickListener {
             if(person.ImageUrl.isBlank()){
                 Toast.makeText(this,"Please Upload Image",Toast.LENGTH_SHORT).show()
             }else {
                    if(binding.editTextTextMultiLine.text.toString()!="") {
                        person.disp = binding.editTextTextMultiLine.text.toString()
                    }
                 Firebase.firestore.collection("Reportings").document(UUID.randomUUID().toString())
                     .set(person).addOnCompleteListener {
                     if (it.isSuccessful) {
                         Toast.makeText(
                             this,
                             "Thankyou! Authority will take required actions",
                             Toast.LENGTH_LONG
                         ).show()
                         finish()
                         var intent=Intent(this,MainActivity::class.java)
                         startActivity(intent)

                     } else {
                         Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
                     }

                 }
             }
         }
        }
    }
}