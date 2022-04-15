package com.example.shareroutine.ui.community

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shareroutine.data.model.Routine
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

        // Test model
        val routine1 = Routine("게시글1", "사용자1")
        val routine2 = Routine("게시글2", "사용자2")
        val routine3 = Routine("게시글3", "사용자1")
        val routine4 = Routine("게시글4", "사용자3")

        // viewModel 적용 필요
        val routineList = listOf(routine1, routine2, routine3, routine4)

        val recyclerView = binding.communityMainList
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = CommunityMainAdapter(routineList)

        viewModel.routineList.observe(viewLifecycleOwner) {
            val adapter = binding.communityMainList.adapter as CommunityMainAdapter
            adapter.setData(it)
        }

        return root
    }
}