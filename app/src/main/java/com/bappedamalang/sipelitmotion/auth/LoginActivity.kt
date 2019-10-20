package com.bappedamalang.sipelitmotion.auth

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.bappedamalang.sipelitmotion.*
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.bappedamalang.sipelitmotion.model.response.ResponseLoginRegister
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import com.google.gson.Gson
import id.flwi.util.ActivityUtil
import kotlinx.android.synthetic.main.layout_login.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity: AppCompatActivity() {

    private var apiService: APIService? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        apiService = RetrofitService.createService(APIService::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Login...")
        cirLoginButton.setOnClickListener {
            login()
        }
        registerButton.setOnClickListener {
            startActivity(Intent(this!!, RegisterActivity::class.java))
            finish()
        }
    }

    fun login() {
        if (TextUtils.isEmpty(editTextEmail.text)) {
            textInputEmail.error = "Email masih kosong"
            return
        }
        if (TextUtils.isEmpty(editTextPassword.text)) {
            editTextPassword.error = "Password masih kosong"
            return
        }

        progressDialog?.show()

        apiService!!.login(editTextEmail.text.toString(), editTextPassword.text.toString()).enqueue(object : Callback<ResponseLoginRegister> {
            override fun onFailure(call: Call<ResponseLoginRegister>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal Login : " + t.message, LENGTH_SHORT).show()
                progressDialog?.dismiss()
            }

            override fun onResponse(
                call: Call<ResponseLoginRegister>,
                response: Response<ResponseLoginRegister>
            ) {
                progressDialog?.dismiss()
                if (response.isSuccessful) {
                    if (response.body() != null && response!!.body()!!.data != null) {
                        var user = response!!.body()!!.data!!
                        ActivityUtil.setSharedPreference(this@LoginActivity, TOKEN, user.token)
                        ActivityUtil.setSharedPreference(this@LoginActivity, EMAIL, user.email)
                        ActivityUtil.setSharedPreference(this@LoginActivity, NAME, user.name)
                        ActivityUtil.setSharedPreference(this@LoginActivity, ADDRESS, user.address)
                        ActivityUtil.setSharedPreference(this@LoginActivity, INSTITUSI, user.institusi)
                        ActivityUtil.setSharedPreference(this@LoginActivity, NO_HP, user.phone)
                        ActivityUtil.setSharedPreference(this@LoginActivity, USER_ID, user.id)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        return
                    }
                }
                Toast.makeText(this@LoginActivity, "Gagal Login ", LENGTH_SHORT).show()
            }

        })


    }
}