package com.bappedamalang.sipelitmotion.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.adapter.SearchKajianAdapter
import com.bappedamalang.sipelitmotion.model.response.ResponseKajian
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), TextWatcher {


    private var apiService: APIService? = null
    var v: View? = null
    var adapter: SearchKajianAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)
        apiService = RetrofitService.createService(APIService::class.java)
        initRecyclerView()
        getAllProduct()
        return v
    }

    fun initRecyclerView() {
        adapter = SearchKajianAdapter(context!!)
        v?.recyclerView!!.layoutManager = LinearLayoutManager(context!!)
        v?.recyclerView!!.adapter = adapter
        v?.searchText?.addTextChangedListener(this)
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

    override fun afterTextChanged(s: Editable?) {
        adapter?.searchKeyword(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}