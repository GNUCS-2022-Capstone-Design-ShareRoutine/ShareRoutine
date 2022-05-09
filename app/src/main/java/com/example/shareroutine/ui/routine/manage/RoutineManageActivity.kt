package com.example.shareroutine.ui.routine.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.shareroutine.databinding.ActivityRoutineAddBinding
import com.google.android.material.tabs.TabLayoutMediator

class RoutineManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoutineAddBinding

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.routineAddPager
        viewPager.adapter = RoutineManagePagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.routineAddTabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "새 루틴 추가"
                1 -> tab.text = "기존 목록에서 추가"
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem != 0) {
            viewPager.currentItem--
        }
        else {
            this.finish()
        }
    }
}