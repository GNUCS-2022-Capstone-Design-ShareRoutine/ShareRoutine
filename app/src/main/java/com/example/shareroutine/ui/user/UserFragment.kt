package com.example.shareroutine.ui.user

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shareroutine.R
import com.example.shareroutine.databinding.UserFragmentBinding
import com.example.shareroutine.ui.routine.manage.RoutineManageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!
    private var textView: TextView? = null
    private var auth: FirebaseAuth? = null


    val database = Firebase.database
    private val myRef = database.reference
    val firebaseUser: FirebaseUser? = auth?.currentUser

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        _binding = UserFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)


        // Observing below

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = auth?.currentUser
        Log.d("info", firebaseUser?.uid.toString())

        textView = view.findViewById(R.id.user_nickname)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val nick = firebaseUser?.let { dataSnapshot.child("users").child(it.uid).child("nickname").value }
                textView!!.text = nick as CharSequence?
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //Log.d("nickname",textView.toString())



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_menu_edit -> {
                val intent = Intent(requireContext(), UserEditActivity::class.java)
                startActivity(intent)
            }
        }
        when (item.itemId) {
            R.id.user_menu_logout -> {

                Firebase.auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext(), "Logout pressed", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}