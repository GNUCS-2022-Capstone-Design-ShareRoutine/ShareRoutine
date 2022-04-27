package com.example.shareroutine.ui.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.TypedArrayUtils.getBoolean
import com.example.shareroutine.MainActivity
import com.example.shareroutine.R
import com.example.shareroutine.data.model.UserAccount
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Boolean.getBoolean
import java.lang.reflect.Array.get
import java.lang.reflect.Array.getBoolean
object PreferenceHelper {

    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit{ it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Error")
        }
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> SharedPreferences.get(key: String, defaultValue: T? = null): T {
        return when (defaultValue) {
            is String, null -> getString(key, defaultValue as? String) as T
            is Int -> getInt(key, defaultValue as? Int ?: -1) as T
            is Boolean -> getBoolean(key, defaultValue as? Boolean ?: false) as T
            is Float -> getFloat(key, defaultValue as? Float ?: -1f) as T
            is Long -> getLong(key, defaultValue as? Long ?: -1) as T
            else -> throw UnsupportedOperationException("Error")
        }
    }

}
class UserLogin : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private lateinit var btn_google // 구글 로그인 버튼
            : SignInButton
    private var auth // 파베 인증 객체
            : FirebaseAuth? = null
    private var googleApiClient // 구글 API 클라이언트 객체
            : GoogleApiClient? = null
    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) { // 앱이 실행될 때 처음 수행되는 곳
        super.onCreate(savedInstanceState)


        //상단바 제거

        setContentView(R.layout.activity_login)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.UserLogin_client_id))
            .requestEmail()
            .build() //기본 옵션 세팅
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build()
        auth = FirebaseAuth.getInstance() // 파이어베이스 인증 객체 초기화.
        mDatabase = FirebaseDatabase.getInstance().reference
        btn_google = findViewById(R.id.btn_google_login)
        btn_google.setOnClickListener(
            View.OnClickListener
            // 구글 로그인 버튼 클릭 시 이곳을 수행.
            {
                val intent: Intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient!!)
                startActivityForResult(intent, REQ_SIGN_GOOGLE)
            })
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) { // 구글 로그인 인증 요청했을 때, 결과 값 되돌려 받는 곳.
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_SIGN_GOOGLE) {
            val result: GoogleSignInResult? = data?.let {
                Auth.GoogleSignInApi.getSignInResultFromIntent(
                    it
                )
            }
            if (result != null) {
                if (result.isSuccess) { // 인증결과가 성공적이면...
                    val account: GoogleSignInAccount? =
                        result.signInAccount // account 라는 데이터는 구글로그인 정보를 담고 있음. (닉네임, 프로필 사진, 이메일 주소 등)
                    if (account != null) {
                        resultLogin(account)
                    } // 로그인 결과값 출력 수행 메소드
                }
            }
        }
    }

    companion object {
        private const val REQ_SIGN_GOOGLE = 100 // 구글 로그인 결과 코드(임시로 선언)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}


//

    private fun resultLogin(account: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this,
                OnCompleteListener<Any?> { task ->
                    if (task.isSuccessful) { // 로그인이 성공했으면 ...
                        val firebaseUser: FirebaseUser? = auth!!.currentUser
                        val account1 = UserAccount()

                        val flag: Boolean = SharedPreferences.getBoolean(
                            this@UserLogin,
                            firebaseUser?.uid.toString()
                        )

                        if (firebaseUser != null) {
                            if (firebaseUser.uid.toString() !== firebaseUser.getIdToken(true)
                                    .toString()
                            ) { //로그인 할 때마다 데이터 베이스에 사용자계정이 중복되어 저장되는 것을 막아준다.
                                if (!flag) {
                                    account1.setIdToken(firebaseUser.uid)
                                    account1.setEmailId(firebaseUser.email)
                                    mDatabase!!.child("UserInfo").child(firebaseUser.uid)
                                        .setValue(account1)
                                }
                                Toast.makeText(this@UserLogin, "로그인 성공", Toast.LENGTH_SHORT).show()
                                val flagFalse =
                                    Intent(applicationContext, MainActivity::class.java)
                                flagFalse.putExtra("nickName", account.displayName)
                                flagFalse.putExtra("email", account.email)
                                flagFalse.putExtra(
                                    "photoUrl",
                                    account.photoUrl.toString()
                                ) // 특정 자료형을 String으로 변환.
                                if (flag) { //최초실행 이후
                                    val flagTrue =
                                        Intent(applicationContext, MainActivity::class.java)
                                    startActivity(flagTrue)
                                } else {
                                    startActivity(flagFalse) //최초실행시 ResultActivity실행 조건문
                                }
                                Log.e(
                                    "spn",
                                    "resultactivity : " + mDatabase!!.child("UserInfo")
                                        .child(firebaseUser.uid).child("std_grade_num").key!!
                                        .isEmpty()
                                )
                            }
                        }
                    } else { // 로그인이 실패했으면 ...
                        Toast.makeText(this@UserLogin, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                })
    }


    private fun Any.addOnCompleteListener(
        userLogin: UserLogin,
        onCompleteListener: OnCompleteListener<Any?>
    ) {

    }
}