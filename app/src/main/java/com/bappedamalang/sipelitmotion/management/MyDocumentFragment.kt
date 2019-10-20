package com.bappedamalang.sipelitmotion.management

import com.bappedamalang.sipelitmotion.USER_ID
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.adapter.KajianAdapter
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.flwi.util.ActivityUtil
import kotlinx.android.synthetic.main.fragment_home.view.*


class MyDocumentFragment : Fragment() {

    private var apiService: APIService? = null
    var v: View? = null
    var adapter: KajianAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_management, container, false)
        apiService = RetrofitService.createService(APIService::class.java)
        initRecyclerView()
        getAllProduct()
        return v
    }

    fun initRecyclerView(){
        adapter = KajianAdapter(context!!)
        v?.recyclerView!!.layoutManager = GridLayoutManager(context!!, 2)
        v?.recyclerView!!.adapter = adapter
    }

    fun getAllProduct() {
        apiService!!.getAllKajianUser(ActivityUtil.getSharedPreferenceString(context!!, USER_ID)).enqueue(object : Callback<ResponseKajian> {
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

}