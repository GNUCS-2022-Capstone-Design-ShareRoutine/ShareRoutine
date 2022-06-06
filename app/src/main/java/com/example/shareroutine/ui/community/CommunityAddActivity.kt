package com.example.shareroutine.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ActivityCommunityAddBinding
import com.example.shareroutine.domain.model.Post
import com.example.shareroutine.ui.adapter.RoutineAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityAddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCommunityAddBinding.inflate(layoutInflater) }

    private lateinit var viewModel: CommunityAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CommunityAddViewModel::class.java]

        supportActionBar?.title = "게시글 작성"

        val postFromDetailActivity = intent.getSerializableExtra("post")

        if (postFromDetailActivity != null) {
            val post = postFromDetailActivity as Post

            viewModel.setPost(post)

            binding.communityAddTitleEdit.text = SpannableStringBuilder(post.title)
            binding.communityAddContentEdit.text = SpannableStringBuilder(post.description)
            binding.communityAddHashtagEdit.text = SpannableStringBuilder(
                post.hashTags.joinToString("#", "#")
            )
        }

        setRecyclerView()
        setButton()
    }

    private fun setRecyclerView() {
        viewModel.routineList.observe(this) { list ->
            val listener = object : RoutineAdapter.ItemClickListener {
                override fun onItemClick(
                    holder: RoutineAdapter.ViewHolder,
                    position: Int,
                    holders: List<RoutineAdapter.ViewHolder>
                ) {
                    val routine = list[position]

                    if (viewModel.selectedRoutine != routine) {
                        viewModel.selectedRoutine = routine

                        holders.map {
                            it.binding.recyclerViewHeaderText.setBackgroundColor(
                                ContextCompat.getColor(
                                    holder.itemView.context,
                                    R.color.share_routine_theme_cream_pink
                                )
                            )
                        }

                        holder.binding.recyclerViewHeaderText.setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.share_routine_theme_dark_purple
                            )
                        )
                    }
                    else {
                        viewModel.selectedRoutine = null

                        holder.binding.recyclerViewHeaderText.setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.share_routine_theme_cream_pink
                            )
                        )
                    }
                }
            }

            binding.communityAddRoutineList.apply {
                layoutManager = LinearLayoutManager(this@CommunityAddActivity)
                adapter = RoutineAdapter(list, listener)
            }
        }
    }

    private fun setButton() {
        binding.communityAddButton.setOnClickListener {
            if (binding.communityAddTitleEdit.text?.isBlank() == true) {
                Toast.makeText(this, "제목을 확인해주세요",
                    Toast.LENGTH_SHORT).show()
            }
            else if (!hashTagOk()) {
                Toast.makeText(this, "해시태그를 확인해주세요",
                    Toast.LENGTH_SHORT).show()
            }
            else if (binding.communityAddContentEdit.text?.isBlank() == true) {
                Toast.makeText(this, "내용을 확인해주세요",
                    Toast.LENGTH_SHORT).show()
            }
            else if (viewModel.selectedRoutine == null) {
                Toast.makeText(this, "루틴을 선택해주세요",
                    Toast.LENGTH_SHORT).show()
            }
            else {
                val title = binding.communityAddTitleEdit.text.toString()
                val hashTag = binding.communityAddHashtagEdit.text.toString()
                val content = binding.communityAddContentEdit.text.toString()

                viewModel.writePost(title, hashTag, content).observe(this) {
                    if (it) {
                        Toast.makeText(this, "게시글을 작성했습니다!",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "게시글 작성에 실패했습니다!",
                            Toast.LENGTH_SHORT).show()
                    }

                    finish()
                }
            }
        }
    }

    private fun hashTagOk(): Boolean {

        // condition 1
        val isNotBlank = binding.communityAddHashtagEdit.text?.isNotBlank() == true

        val hashTagText = binding.communityAddHashtagEdit.text.toString()

        // condition 2
        val containsHash = hashTagText.contains("#")

        val hashCount = hashTagText.count{ it == '#' }
        val tagList = hashTagText.split("#")

        // condition 3
        val sameSizeAndCount = hashCount == tagList.size - 1

        val tagWithNothing = mutableListOf<String>()

        tagList.filter { it.isEmpty() }.map { tagWithNothing.add(it) }

        if (tagWithNothing.isNotEmpty()) { tagWithNothing.removeFirst() }

        // condition 4
        val isAllValid = tagWithNothing.isEmpty()

        return isNotBlank && containsHash && sameSizeAndCount && isAllValid
    }
}