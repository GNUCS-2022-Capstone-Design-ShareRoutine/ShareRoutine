package com.example.shareroutine.data.model

import android.provider.ContactsContract


class UserAccount // 빈 생성자를 선언해줘야 firebase에 값을 가져오고 넣을 때, 오류가 생기지 않음
{

    private var idToken // Firebase Uid(고유 토튼정보(프라이머리 키))
            : String? = null
    private var emailId // 이메일 아이디
            : String? = null
    private var nickname
            : String? = null

    fun UserAccount() {} // 빈 생성자를 선언해줘야 firebase에 값을 가져오고 넣을 때, 오류가 생기지 않음

    fun getIdToken(): String? {
        return idToken
    }

    fun setIdToken(idToken: String?) {
        this.idToken = idToken
    }

    fun getEmailId(): String? {
        return emailId
    }

    fun setEmailId(emailId: String?) {
        this.emailId = emailId
    }
    fun setNickname(nickname: String?){
        this.nickname = nickname
    }

    fun getNickname(): String?{
        return nickname
    }

}