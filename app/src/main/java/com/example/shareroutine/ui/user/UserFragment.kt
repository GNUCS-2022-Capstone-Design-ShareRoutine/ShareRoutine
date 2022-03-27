package com.example.shareroutine.ui.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareroutine.R
import com.example.shareroutine.databinding.UserFragmentBinding

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

        // Observing below

        return root
    }
}