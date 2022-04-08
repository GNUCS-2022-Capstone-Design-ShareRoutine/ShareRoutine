package com.example.shareroutine.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shareroutine.databinding.CommunityFragmentBinding

class CommunityFragment : Fragment() {

    private var _binding: CommunityFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CommunityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CommunityViewModel::class.java]

        _binding = CommunityFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.communityMainList

        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = CommunityMainAdapter(emptyList())

        viewModel.routineList.observe(viewLifecycleOwner) {
            val adapter = binding.communityMainList.adapter as CommunityMainAdapter
            adapter.setData(it)
        }

        return root
    }
}