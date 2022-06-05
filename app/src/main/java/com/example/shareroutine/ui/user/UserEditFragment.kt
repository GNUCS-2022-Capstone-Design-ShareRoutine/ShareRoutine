package com.example.shareroutine.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shareroutine.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserEditFragment : Fragment() {
    val database = Firebase.database
    val myRef = database.getReference("users")


    companion object {
        fun newInstance() = UserEditFragment()
    }

    private lateinit var viewModel: UserEditViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        return inflater.inflate(R.layout.fragment_user_edit, container, false)
        //val FirebaseInstanceId:FirebaseDatabase

        auth = Firebase.auth
        val test: String? = auth.uid
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.child("sofa")
                for(ds in test.children){
                    Log.e("snap", ds.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 읽어오기에 실패했을 때
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserEditViewModel::class.java)

        // TODO: Use the ViewModel
    }

}