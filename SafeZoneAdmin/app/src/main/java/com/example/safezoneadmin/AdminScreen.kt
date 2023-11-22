package com.example.safezoneadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safezoneadmin.databinding.ActivityAdminScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class AdminScreen : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdminScreenBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: PersonAdapter
    private lateinit var datalist: ArrayList<PersonModel>
    private lateinit var snapshotListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        datalist = ArrayList()
        adapter = PersonAdapter(datalist,this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        fetchDataFromFirestore()

    }

    private fun fetchDataFromFirestore() {
        snapshotListener = FirebaseFirestore.getInstance().collection("Reportings")
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: Exception? ->
                if (exception != null) {
                    return@addSnapshotListener
                }

                datalist.clear()

                val documents = snapshot?.documents
                if (documents != null) {
                    for (documentSnapshot in documents) {
                        val temp = documentSnapshot.toObject<PersonModel>()
                        temp?.let { datalist.add(it) }
                    }
                }

                // Move notifyDataSetChanged inside the listener
                adapter.notifyDataSetChanged()

                Log.d("checkee", datalist.toString())
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the snapshot listener when the activity is destroyed
        snapshotListener.remove()
    }
}

