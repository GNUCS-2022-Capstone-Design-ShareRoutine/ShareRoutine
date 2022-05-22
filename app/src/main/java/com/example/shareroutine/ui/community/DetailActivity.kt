package com.example.shareroutine.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareroutine.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title = binding.detailRoutineTitle
        title.text = intent.getStringExtra("title")

        val username = binding.detailRoutineUsername
        username.text = intent.getStringExtra("username")

        // 제목 지정
        // 해시태그 Chip Post 에서 받아와서 자동으로 넣어지게 하기
    }
}