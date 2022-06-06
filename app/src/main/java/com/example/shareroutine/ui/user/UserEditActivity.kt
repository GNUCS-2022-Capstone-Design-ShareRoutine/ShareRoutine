package com.example.shareroutine.ui.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.shareroutine.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserEditActivity : AppCompatActivity() {

    private var textView: TextView? = null
    private var editText: EditText? = null
    private var button: Button? = null
    private var auth: FirebaseAuth? = null
    private lateinit var databaseReference: DatabaseReference
    val database = Firebase.database
    private val myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit)
        databaseReference = Firebase.database.reference

        textView = findViewById(R.id.tv_nickname)
        editText = findViewById(R.id.edit_nickname)
        button = findViewById(R.id.edit_nickname_button)

    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = auth!!.currentUser
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val text = firebaseUser?.let { dataSnapshot.child("users").child(it.uid).child("nickname").value } as String
                textView!!.text = text as CharSequence?
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        button!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if (firebaseUser != null) {
                    myRef.child("users").child(firebaseUser.uid).child("nickname").setValue(editText!!.text.toString())
                }
                Toast.makeText(this@UserEditActivity, "변경 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}