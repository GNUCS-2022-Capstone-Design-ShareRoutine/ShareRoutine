package com.example.shareroutine.ui.routine

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.shareroutine.R
import com.example.shareroutine.databinding.RoutineFragmentBinding
import com.example.shareroutine.ui.routine.manage.RoutineManageActivity

class RoutineFragment : Fragment() {

    private var _binding: RoutineFragmentBinding? = null
    private val binding get() =_binding!!

    private lateinit var backPressedCallback: OnBackPressedCallback
    private lateinit var viewPager:  ViewPager2
    private lateinit var viewModel: RoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RoutineViewModel::class.java]

        _binding = RoutineFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        viewPager = binding.routinePager
        viewPager.adapter = RoutinePagerAdapter(this)

        // Observing below

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.routine_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.routine_menu_manage -> {
                val intent = Intent(requireContext(), RoutineManageActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewPager.currentItem != 0) {
                    viewPager.currentItem--
                }
                else {
                    requireActivity().finish()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
}