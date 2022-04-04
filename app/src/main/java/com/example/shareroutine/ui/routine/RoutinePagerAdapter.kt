package com.example.shareroutine.ui.routine

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shareroutine.ui.routine.detail.RoutineDetailFragment
import com.example.shareroutine.ui.routine.main.RoutineMainFragment

class RoutinePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments: List<Fragment> = listOf(
            RoutineMainFragment(), RoutineDetailFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}