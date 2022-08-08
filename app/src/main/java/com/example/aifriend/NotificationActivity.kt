package com.example.aifriend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aifriend.data.notificationData
import com.example.aifriend.databinding.NotificationBinding
import com.example.aifriend.recycler.notificationAdapter

class NotificationActivity: AppCompatActivity() {
    lateinit var binding: NotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeRecyclerView()
    }
    private fun makeRecyclerView(){
        val email = MyApplication.email
        MyApplication.db.collection("user").document(email.toString())
            .collection("notification")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<notificationData>()
                for (document in result){
                    val item = document.toObject(notificationData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                //var itemSort = itemList.sortByDescending { it.time}  as List<notificationData>
                binding.notiRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.notiRecyclerView.adapter = notificationAdapter(this, itemList)
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "서버로부터 데이터 획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
     }
}