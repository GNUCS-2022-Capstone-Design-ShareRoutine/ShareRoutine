package com.example.shareroutine.ui.routine.manage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shareroutine.ui.routine.manage.exist.ExistRoutineFragment
import com.example.shareroutine.ui.routine.manage.fresh.NewRoutineFragment

class RoutineManagePagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {

    private val fragments: List<Fragment> = listOf(
        NewRoutineFragment(), ExistRoutineFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}