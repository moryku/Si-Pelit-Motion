package com.bappedamalang.sipelitmotion.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bappedamalang.sipelitmotion.BASE_URL_FILE
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.detail.DetailKajianActivity
import com.bappedamalang.sipelitmotion.model.MKajian
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_horizontal_kajian.view.*

class KajianAdapter (var context: Context): RecyclerView.Adapter<KajianAdapter.PlaceViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var data: List<MKajian> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = inflater.inflate(R.layout.item_horizontal_kajian, parent, false)
        return PlaceViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.setData(data.get(position))
    }

    inner class PlaceViewHolder (var v: View): RecyclerView.ViewHolder(v) {
        fun setData(kajian: MKajian){
            v.title.text = kajian.judul
            v.jumlah_hal.text = kajian.jumlah_hal.toString() + " Hal"
            Glide.with(context)
                .load(BASE_URL_FILE + kajian.image)
                .into(v.image)
            v.master_layout.setOnClickListener {
                context.startActivity(Intent(context, DetailKajianActivity::class.java).putExtra("data", kajian))
            }
        }
    }

    internal fun setData(datas: MutableList<MKajian>) {
        this.data = datas
        notifyDataSetChanged()
    }

}