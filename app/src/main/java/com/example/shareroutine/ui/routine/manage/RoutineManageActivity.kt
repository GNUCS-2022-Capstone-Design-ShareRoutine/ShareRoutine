package com.example.shareroutine.ui.routine.manage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.shareroutine.databinding.ActivityRoutineAddBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineManageActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private val binding by lazy { ActivityRoutineAddBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewPager = binding.routineAddPager
        viewPager.adapter = RoutineManagePagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.routineAddTabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "루틴 작성"
                1 -> tab.text = "기존 목록에서 수정"
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