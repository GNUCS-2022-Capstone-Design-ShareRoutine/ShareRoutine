package com.example.shareroutine.ui.user

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shareroutine.databinding.ActivityUserEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserEditBinding.inflate(layoutInflater) }

    private lateinit var viewModel: UserEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserEditViewModel::class.java]

        actionBar?.title = "정보 수정"

        observeUser()
        setButton()
    }

    private fun observeUser() {
        viewModel.user.observe(this) {
            binding.prevNickname.text = it.nickname
        }
    }

    private fun setButton() {
        binding.editNicknameButton.setOnClickListener {
            val newNickname = binding.newNicknameEdit.text.toString()

            if (newNickname.isBlank()) {
                Toast.makeText(
                    this,
                    "한 글자 이상 입력해주세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                viewModel.changeNickname(newNickname).observe(this) {
                    if (it) {
                        Toast.makeText(
                            this,
                            "닉네임이 변경되었습니다!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}