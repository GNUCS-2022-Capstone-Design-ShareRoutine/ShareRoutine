package com.example.shareroutine.ui.routine.manage.fresh.add_todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareroutine.databinding.ActivityAddTodoBinding
import com.example.shareroutine.domain.model.Term
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AddTodoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddTodoBinding.inflate(layoutInflater) }

    private val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(ZonedDateTime.now().hour)
        .setMinute(ZonedDateTime.now().minute)
        .setTitleText("시간 설정")
        .build()

    private var time: LocalTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "할 일 추가"

        val term = intent.getSerializableExtra("term") as Term
        setTimeText(term)

        binding.newRoutineTodoTime.setOnClickListener {
            when (term) {
                Term.DAILY -> timePicker.show(supportFragmentManager, "daily")
                Term.WEEKLY -> {}
                Term.MONTHLY -> {}
                Term.YEARLY -> {}
                Term.NONE -> {}
            }
        }

        timePicker.addOnPositiveButtonClickListener {
            val formatter = DateTimeFormatter.ofPattern("a hh시 mm분")

            val hourText = if (timePicker.hour < 10) {
                "0${timePicker.hour}"
            }
            else {
                timePicker.hour.toString()
            }

            val minuteText = if (timePicker.minute < 10) {
                "0${timePicker.minute}"
            }
            else {
                timePicker.minute.toString()
            }

            time = LocalTime.parse("${hourText}:${minuteText}")

            binding.newRoutineTodoTime.text = formatter.format(time)
        }

        binding.addTodoButton.setOnClickListener {
            when (term) {
                Term.DAILY -> intent.putExtra("time", time)
                Term.WEEKLY -> {}
                Term.MONTHLY -> {}
                Term.YEARLY -> {}
                Term.NONE -> {}
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setTimeText(term: Term) {
        val nowTime = ZonedDateTime.now()

        val timeText = when (term) {
            Term.DAILY -> {
                val formatter = DateTimeFormatter.ofPattern("a hh시 mm분")
                formatter.format(nowTime)
            }
            Term.WEEKLY -> {
                val formatter = DateTimeFormatter.ofPattern("E")

                val dayOfWeek = when (formatter.format(nowTime)) {
                    "Mon" -> "월요일"
                    "Tue" -> "화요일"
                    "Wed" -> "수요일"
                    "Thu" -> "목요일"
                    "Fri" -> "금요일"
                    "Sat" -> "토요일"
                    "Sun" -> "일요일"
                    else -> "알 수 없는 요일"
                }

                dayOfWeek
            }
            Term.MONTHLY -> {
                val formatter = DateTimeFormatter.ofPattern("dd일")
                formatter.format(nowTime)
            }
            Term.YEARLY -> {
                val formatter = DateTimeFormatter.ofPattern("MM월")
                formatter.format(nowTime)
            }
            Term.NONE -> "알 수 없는 오류"
        }

        binding.newRoutineTodoTime.text = timeText
    }
}