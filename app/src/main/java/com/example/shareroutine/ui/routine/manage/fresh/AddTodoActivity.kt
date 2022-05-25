package com.example.shareroutine.ui.routine.manage.fresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shareroutine.databinding.ActivityAddTodoBinding
import com.example.shareroutine.domain.model.Term

class AddTodoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddTodoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        actionBar?.title = "할 일 추가"

        val term = intent.getSerializableExtra("term") as Term

        Log.d("AddTodoActivity", term.toString())

        binding.addTodoButton.setOnClickListener {
            intent.putExtra("result", "Result Yay")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}