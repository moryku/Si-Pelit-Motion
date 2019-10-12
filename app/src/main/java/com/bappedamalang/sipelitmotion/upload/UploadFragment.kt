package com.bappedamalang.sipelitmotion.upload

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bappedamalang.sipelitmotion.model.response.ResponseCategory
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import kotlinx.android.synthetic.main.fragment_upload.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.app.ActivityCompat.startActivityForResult
import android.content.Intent
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.model.MCategory


class UploadFragment : Fragment(), AdapterView.OnItemSelectedListener {


    var v: View? = null
    private var apiService: APIService? = null
    var category: MCategory? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_upload, container, false)
        apiService = RetrofitService.createService(APIService::class.java)
        initListener()
        getAllCategory()
        return v
    }

    fun getAllCategory() {
        apiService!!.getAllKategory().enqueue(object : Callback<ResponseCategory> {
            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                Log.d("error", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseCategory>,
                response: Response<ResponseCategory>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null && response!!.body()!!.data != null && response!!.body()!!.data.size > 0) {
                        val categoryAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, response!!.body()!!.data)
                        v?.spinner?.adapter = categoryAdapter
                    }
                }
            }

        })
    }

    fun initListener() {
        v?.spinner?.onItemSelectedListener = this
        v?.buttonChooseFile?.setOnClickListener {
            var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.type = "pdf/*"
            chooseFile = Intent.createChooser(chooseFile, "Choose a file")
            startActivityForResult(chooseFile, 1)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}