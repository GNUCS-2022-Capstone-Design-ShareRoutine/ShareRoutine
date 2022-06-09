package com.example.shareroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shareroutine.databinding.ActivityStartBinding
import com.example.shareroutine.ui.user.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        autoLogin()
    }

    private fun autoLogin(){
        val mAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth.currentUser

        println("StartActivity: $user")

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            user.getIdToken(true).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // val idToken: String? = task.result.token
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }

                finish()
            }
        }


    }
}