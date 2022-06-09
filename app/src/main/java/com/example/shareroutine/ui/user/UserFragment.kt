package com.example.shareroutine.ui.user

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shareroutine.R
import com.example.shareroutine.databinding.UserFragmentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        viewModel.user.observe(viewLifecycleOwner) {
            val nicknameText = "${it.nickname}님!"

            binding.userNickname.text = nicknameText
        }

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

                Firebase.auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext(), "앱에서 로그아웃합니다!", Toast.LENGTH_LONG).show()

                requireActivity().finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}