package com.example.shareroutine.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareroutine.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = binding.detailRoutineTitle
        title.text = intent.getStringExtra("title")

        val username = binding.detailRoutineUsername
        username.text = intent.getStringExtra("username")
    }
}