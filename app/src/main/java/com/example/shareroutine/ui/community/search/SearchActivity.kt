package com.example.shareroutine.ui.community.search

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ActivitySearchBinding
import com.example.shareroutine.ui.community.CommunityMainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        supportActionBar?.title = "검색"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_menu_button)?.actionView as androidx.appcompat.widget.SearchView

        val query = intent.getStringExtra("query")
        println("SearchActivity: $query")

        menu.findItem(R.id.search_menu_button)?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                searchView.onActionViewExpanded()

                searchView.setQuery(query, false)

                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?) = true
        })

        if (query != null) {
            menu.findItem(R.id.search_menu_button).expandActionView()
        }

        val listener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!! != "") {
                    viewModel.searchWithHashTag(query).observe(this@SearchActivity) {
                        if (it) {
                            binding.searchResultList.apply {
                                layoutManager = GridLayoutManager(this@SearchActivity, 2)
                                adapter = CommunityMainAdapter(viewModel.data)
                            }
                        }
                    }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // 매 입력마다
                return true
            }
        }

        searchView.setOnQueryTextListener(listener)

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}