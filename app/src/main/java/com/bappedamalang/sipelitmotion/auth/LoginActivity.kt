package com.bappedamalang.sipelitmotion.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bappedamalang.sipelitmotion.MainActivity
import com.bappedamalang.sipelitmotion.R
import kotlinx.android.synthetic.main.layout_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        cirLoginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}