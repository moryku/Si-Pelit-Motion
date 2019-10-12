package com.bappedamalang.sipelitmotion.home

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.adapter.KajianAdapter
import com.bappedamalang.sipelitmotion.adapter.KategoryAdapter
import com.bappedamalang.sipelitmotion.model.response.ResponseCategory
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import id.flwi.util.ActivityUtil
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private var apiService: APIService? = null
    var v: View? = null
    var adapter: KajianAdapter? = null
    var categoryAdapter: KategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        apiService = RetrofitService.createService(APIService::class.java)
        initRecyclerView()
        getAllProduct()
        categoryRecyclerView()
        getAllCategory()
        return v
    }

    fun initRecyclerView(){
        adapter = KajianAdapter(context!!)
        v?.recyclerView!!.layoutManager = GridLayoutManager(context!!, 2)
        v?.recyclerView!!.adapter = adapter
    }

    fun categoryRecyclerView(){
        val layoutManager = FlexboxLayoutManager(context)
        categoryAdapter = KategoryAdapter(context!!)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        v!!.categoryRecyclerView.setLayoutManager(layoutManager)
        v!!.categoryRecyclerView.adapter = categoryAdapter

    }

    fun getAllProduct() {
        apiService!!.getAllKajian().enqueue(object : Callback<ResponseKajian> {
            override fun onFailure(call: Call<ResponseKajian>, t: Throwable) {
                Log.d("error", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseKajian>,
                response: Response<ResponseKajian>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null && response!!.body()!!.data != null && response!!.body()!!.data.size > 0) {
                        adapter?.setData(response!!.body()!!.data!!.toMutableList())
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

        })
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
                        categoryAdapter?.setData(response!!.body()!!.data!!.toMutableList())
                    }
                }
            }

        })
    }
}