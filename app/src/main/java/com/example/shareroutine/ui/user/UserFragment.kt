package com.example.shareroutine.ui.user

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shareroutine.R
import com.example.shareroutine.databinding.UserFragmentBinding
import com.example.shareroutine.ui.routine.manage.RoutineManageActivity

class UserFragment : Fragment() {

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        _binding = UserFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        // Observing below

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_menu_edit -> {
                val intent = Intent(requireContext(), UserEditActivity::class.java)
                startActivity(intent)
            }
        }
        when (item.itemId) {
            R.id.user_menu_logout -> {
                Toast.makeText(requireContext(), "Logout pressed", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}