package com.bappedamalang.sipelitmotion.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bappedamalang.sipelitmotion.*
import com.bappedamalang.sipelitmotion.model.response.ResponseLoginRegister
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import id.flwi.util.ActivityUtil
import kotlinx.android.synthetic.main.layout_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private var apiService: APIService? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        apiService = RetrofitService.createService(APIService::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Register...")
        registerButton.setOnClickListener {
            register()
        }
        loginButtn.setOnClickListener {
            startActivity(Intent(this!!, LoginActivity::class.java))
            finish()
        }
    }

    fun register() {
        if (TextUtils.isEmpty(nameEditText.text)) {
            nameEditText.error = "Nama masih kosong"
            return
        }

        if (TextUtils.isEmpty(emailEditText.text)) {
            emailEditText.error = "Email masih kosong"
            return
        }

        if (TextUtils.isEmpty(passwordEditText.text)) {
            passwordEditText.error = "Password masih kosong"
            return
        }

        if (TextUtils.isEmpty(noHpEditText.text)) {
            noHpEditText.error = "No HP masih kosong"
            return
        }

        if (TextUtils.isEmpty(institusiEditText.text)) {
            institusiEditText.error = "Institusi masih kosong"
            return
        }

        if (TextUtils.isEmpty(alamatEditText.text)) {
            alamatEditText.error = "Alamat masih kosong"
            return
        }


        progressDialog?.show()

        apiService!!.register(emailEditText.text.toString(), 
            passwordEditText.text.toString(),
            institusiEditText.text.toString(),
            nameEditText.text.toString(),
            alamatEditText.text.toString(),
            noHpEditText.text.toString()
            ).enqueue(object :
            Callback<ResponseLoginRegister> {
            override fun onFailure(call: Call<ResponseLoginRegister>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Gagal Register : " + t.message, Toast.LENGTH_SHORT).show()
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
                        ActivityUtil.setSharedPreference(this@RegisterActivity, TOKEN, user.token)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, EMAIL, user.email)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, NAME, user.name)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, ADDRESS, user.address)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, INSTITUSI, user.institusi)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, NO_HP, user.phone)
                        ActivityUtil.setSharedPreference(this@RegisterActivity, USER_ID, user.id)
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        return
                    }
                }
                Toast.makeText(this@RegisterActivity, "Gagal Register ", Toast.LENGTH_SHORT).show()
            }

        })


    }
}