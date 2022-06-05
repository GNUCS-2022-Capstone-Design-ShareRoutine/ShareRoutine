package com.example.shareroutine.ui.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.shareroutine.R
import com.example.shareroutine.databinding.ActivityCommunityAddBinding
import com.example.shareroutine.databinding.ActivityMainBinding

class CommunityAddActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommunityAddBinding
    private lateinit var add_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommunityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_community_add)

        val fragment = Fragment()


    }

}