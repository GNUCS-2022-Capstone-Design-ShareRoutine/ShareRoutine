package com.example.shareroutine.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.shareroutine.databinding.ActivityDetailBinding
import com.example.shareroutine.domain.model.Post
import com.google.android.material.chip.Chip

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "상세 내용"

        val post = intent.getSerializableExtra("post") as Post

        binding.detailRoutineTitle.text = post.title
        binding.detailRoutineUsername.text = post.user.nickname
        binding.detailRoutineDescription.text = post.description

        post.hashTags.map {
            val chip = Chip(this)

            chip.text = it
            chip.setOnClickListener {
                Log.d("Chip clicked", "${chip.text}")
            }

            binding.detailRoutineHashGroup.addView(chip)
        }

        val fragment = binding.detailBottomSheet.getFragment<TodoListFragment>()

        val bundle = bundleOf(Pair("routine", post.routine))
        fragment.arguments = bundle
    }
}