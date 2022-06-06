package com.example.shareroutine.ui.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.os.bundleOf
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

        viewModel.setPost(intent.getSerializableExtra("post") as Post)

        binding.detailRoutineTitle.text = viewModel.currentPost!!.title
        binding.detailRoutineUsername.text = viewModel.currentPost!!.user.nickname
        binding.detailRoutineDescription.text = viewModel.currentPost!!.description

        viewModel.currentPost!!.hashTags.map {
            val chip = Chip(this)

            chip.text = it
            chip.setOnClickListener {
                Log.d("Chip clicked", "${chip.text}")
            }

            binding.detailRoutineHashGroup.addView(chip)
        }

        val fragment = binding.detailBottomSheet.getFragment<TodoListFragment>()

        val bundle = bundleOf(Pair("routine", viewModel.currentPost!!.routine))
        fragment.arguments = bundle
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
                finish()
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
}