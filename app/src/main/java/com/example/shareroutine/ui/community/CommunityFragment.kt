package com.example.shareroutine.ui.community

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.CommunityFragmentBinding
import com.example.shareroutine.ui.community.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        setHasOptionsMenu(true)

        val recyclerView = binding.communityMainList
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = CommunityMainAdapter(emptyList())

        viewModel.posts.observe(viewLifecycleOwner) {
            val adapter = binding.communityMainList.adapter as CommunityMainAdapter
            adapter.setData(it)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.community_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.community_menu_add -> {
                val intent = Intent(context, CommunityAddActivity::class.java)
                startActivity(intent)
            }
            R.id.community_menu_search -> {
                val intent = Intent(requireActivity(), SearchActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}