package com.example.shareroutine.ui.routine.manage.fresh.add_todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareroutine.databinding.ActivityAddTodoBinding
import com.example.shareroutine.domain.model.Term
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.*
import java.time.format.DateTimeFormatter

class AddTodoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddTodoBinding.inflate(layoutInflater) }
    private val selectDialog by lazy { MaterialAlertDialogBuilder(this) }

    private val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(ZonedDateTime.now().hour)
        .setMinute(ZonedDateTime.now().minute)
        .setTitleText("시간 설정")
        .build()

    private var time: LocalTime = ZonedDateTime.now().toLocalTime()
    private var dayOfWeek: DayOfWeek = ZonedDateTime.now().dayOfWeek
    private var day: Int = ZonedDateTime.now().dayOfMonth
    private var month: Month = ZonedDateTime.now().month

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "할 일 추가"

        val term = intent.getSerializableExtra("term") as Term
        setTimeText(term)

        binding.newRoutineTodoTime.setOnClickListener {
            when (term) {
                Term.DAILY -> timePicker.show(supportFragmentManager, "daily")
                Term.WEEKLY -> {
                    val dayOfWeeks = arrayOf("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

                    selectDialog.setTitle("요일 선택")
                        .setItems(dayOfWeeks) { _, which ->
                            binding.newRoutineTodoTime.text = dayOfWeeks[which]
                            dayOfWeek = DayOfWeek.of(which + 1)
                        }.show()
                }
                Term.MONTHLY -> {
                    val days = 1.rangeTo(30).map { it.toString() }.toTypedArray()

                    selectDialog.setTitle("요일 선택")
                        .setItems(days) { _, which ->
                            val dayText = "${days[which]}일"
                            binding.newRoutineTodoTime.text = dayText
                            day = which + 1
                        }.show()
                }
                Term.YEARLY -> {
                    val months = 1.rangeTo(12).map { it.toString() }.toTypedArray()

                    selectDialog.setTitle("요일 선택")
                        .setItems(months) { _, which ->
                            val dayText = "${months[which]}월"
                            binding.newRoutineTodoTime.text = dayText
                            month = Month.of(which + 1)
                        }.show()
                }
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
                Term.WEEKLY -> intent.putExtra("dayOfWeek", dayOfWeek)
                Term.MONTHLY -> intent.putExtra("day", day)
                Term.YEARLY -> intent.putExtra("month", month)
                Term.NONE -> {}
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setTimeText(term: Term) {
        val timeText = when (term) {
            Term.DAILY -> {
                val formatter = DateTimeFormatter.ofPattern("a hh시 mm분")
                formatter.format(time)
            }
            Term.WEEKLY -> {
                val dayOfWeekText = when (dayOfWeek) {
                    DayOfWeek.MONDAY -> "월요일"
                    DayOfWeek.TUESDAY -> "화요일"
                    DayOfWeek.WEDNESDAY -> "수요일"
                    DayOfWeek.THURSDAY -> "목요일"
                    DayOfWeek.FRIDAY -> "금요일"
                    DayOfWeek.SATURDAY -> "토요일"
                    DayOfWeek.SUNDAY -> "일요일"
                }

                dayOfWeekText
            }
            Term.MONTHLY -> "${day}일"
            Term.YEARLY -> "${month.value}월"
            Term.NONE -> "알 수 없는 오류"
        }

        binding.newRoutineTodoTime.text = timeText
    }
}