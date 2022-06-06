package com.example.shareroutine.ui.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ActivityDetailBinding
import com.example.shareroutine.domain.model.Post
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        supportActionBar?.title = "상세 내용"

        observePost()
        setButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        if (viewModel.currentPost!!.user.id == currentUser.uid) {
            menuInflater.inflate(R.menu.detail_menu, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_menu_edit -> {
                val intent = Intent(this, CommunityAddActivity::class.java)
                intent.putExtra("post", viewModel.currentPost)
                startActivity(intent)
            }
            R.id.detail_menu_delete -> {
                viewModel.deleteCurrentPost().observe(this) {
                    if (it) {
                        Toast.makeText(this, "게시글을 삭제합니다!",
                        Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this, "게시글을 삭제할 수 없습니다!",
                            Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observePost() {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        viewModel.setPost(intent.getSerializableExtra("post") as Post)

        viewModel.observePost(viewModel.currentPost!!.id!!).observe(this) { post ->
            viewModel.currentPost = post

            binding.detailRoutineTitle.text = post.title
            binding.detailRoutineUsername.text = post.user.nickname
            binding.detailRoutineDescription.text = post.description
            binding.detailRoutineHashGroup.removeAllViews()

            post.hashTags.map { tag ->
                val chip = Chip(this)

                chip.text = tag
                chip.setOnClickListener {
                    Log.d("Chip clicked", "${chip.text}")
                }

                binding.detailRoutineHashGroup.addView(chip)
            }

            if (post.liked.none { it == currentUser.uid }) {
                binding.detailLikeButton.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.share_routine_theme_cream_pink)
                )
            }
            else {
                binding.detailLikeButton.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.share_routine_theme_dark_purple)
                )
            }

            val fragment = binding.detailBottomSheet.getFragment<TodoListFragment>()

            fragment.updateView(post.routine)
        }
    }

    private fun setButton() {
        val currentUser = FirebaseAuth.getInstance().currentUser!!

        binding.detailLikeButton.setOnClickListener {
            viewModel.likePost().observe(this) { result ->
                if (result) {
                    if (viewModel.currentPost!!.liked.none { it == currentUser.uid }) {
                        Toast.makeText(this, "추천을 취소했습니다!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "게시물을 추천했습니다!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}