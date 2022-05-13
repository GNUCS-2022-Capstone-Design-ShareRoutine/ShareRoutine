package com.example.shareroutine.ui.community.search

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_menu_button)?.actionView as androidx.appcompat.widget.SearchView

        val listener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 확인 버튼 입력할 때마다
                println("Submitted $query")
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